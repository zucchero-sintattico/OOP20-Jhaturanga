package jhaturanga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jhaturanga.model.editor.Editor;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypeBuilder;
import jhaturanga.model.game.gametypes.GameTypeBuilderImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.movement.NoCastlingMovementManager;
import jhaturanga.model.piece.movement.NoCastlingPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.User;

public final class ModelImpl implements Model {

    private User firstUser;
    private User secondUser;
    private final List<Match> matches = new ArrayList<>();
    private Player whitePlayer;
    private Player blackPlayer;
    private Timer timer;
    private GameTypesEnum selectedType;
    private Optional<GameType> dynamicGameType = Optional.empty();
    private Editor editor = new EditorImpl();

    @Override
    public Optional<Match> getActualMatch() {
        return this.matches.stream().reduce((a, b) -> b);
    }

    @Override
    public void createMatch() {
        this.setupDynamicGameTypeIfPresent();
        Match match;
        if (this.dynamicGameType.isPresent()) {
            match = new MatchImpl(this.dynamicGameType.get(), this.getTimer());
        } else {
            match = new MatchImpl(this.getGameType().get().getGameType(this.whitePlayer, this.blackPlayer),
                    this.getTimer());
        }
        this.matches.add(match);
    }

    private void setupDynamicGameTypeIfPresent() {
        this.getEditor().getCreatedBoard().ifPresent(x -> {
            final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
            final int columns = this.getEditor().getCreatedBoard().get().getY().getX();
            final int rows = this.getEditor().getCreatedBoard().get().getY().getY();
            final GameController gameController = new ClassicGameController(
                    new StartingBoardFactoryImpl().customizedBoard(this.getEditor().getCreatedBoard().get().getX(),
                            columns, rows, this.whitePlayer, this.blackPlayer),
                    new NoCastlingPieceMovementStrategyFactory(), List.of(this.whitePlayer, this.blackPlayer));
            this.dynamicGameType = Optional.of(gameTypeBuilder.gameController(gameController)
                    .movementManager(new NoCastlingMovementManager(gameController))
                    .gameTypeName("Customizable Board Variant").build());
        });
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.selectedType = gameType;
    }

    @Override
    public void setTimer(final DefaultsTimers timer) {
        if (!timer.getIncrement().isPresent()) {
            this.timer = new TimerFactoryImpl().equalTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds());
        } else {
            this.timer = new TimerFactoryImpl().incrementableTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds(), timer.getIncrement().get());
        }
    }

    @Override
    public Optional<Timer> getTimer() {
        return Optional.ofNullable(this.timer);
    }

    @Override
    public Optional<GameTypesEnum> getGameType() {
        return Optional.ofNullable(this.selectedType);
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.whitePlayer = player;
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.blackPlayer = player;
    }

    @Override
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    @Override
    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Optional<User> getFirstUser() {
        return Optional.ofNullable(this.firstUser);
    }

    @Override
    public Optional<User> getSecondUser() {
        return Optional.ofNullable(this.secondUser);
    }

    @Override
    public void setFirstUser(final User user) {
        this.firstUser = user;
    }

    @Override
    public void setSecondUser(final User user) {
        this.secondUser = user;

    }

    @Override
    public Editor getEditor() {
        return this.editor;
    }

    @Override
    public void clearMatchInfo() {
        this.editor = new EditorImpl();
        this.selectedType = null;
        this.dynamicGameType = Optional.empty();
    }

    @Override
    public String getGameTypeName() {
        return this.getGameType().isPresent() ? this.getGameType().get().toString()
                : this.dynamicGameType.get().getGameName();
    }

}
