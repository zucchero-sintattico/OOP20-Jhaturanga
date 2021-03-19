package jhaturanga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jhaturanga.commons.Pair;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypeFactoryImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
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
    private GameType dynamicGameType;
    private Pair<String, Pair<Integer, Integer>> customizedStartingBoard;

    @Override
    public Optional<Match> getActualMatch() {
        return this.matches.stream().reduce((a, b) -> b);
    }

    @Override
    public void createMatch() {
        Optional.ofNullable(this.customizedStartingBoard).ifPresent(board -> {
            this.dynamicGameType = new GameTypeFactoryImpl().customizedBoardVariantGame(this.whitePlayer,
                    this.blackPlayer, board);
        });

        Optional.ofNullable(this.dynamicGameType)
                .ifPresentOrElse(gameType -> this.matches.add(new MatchImpl(gameType, this.getTimer())),
                        () -> this.matches.add(
                                new MatchImpl(this.getGameType().get().getGameType(this.whitePlayer, this.blackPlayer),
                                        this.getTimer())));
    }

    @Override
    public void setDynamicGameTypeStartingBoard(final Pair<String, Pair<Integer, Integer>> startingBoardInfo) {
        this.customizedStartingBoard = startingBoardInfo;
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.selectedType = gameType;
    }

    @Override
    public void setTimer(final DefaultsTimers timer) {
        timer.getIncrement().ifPresentOrElse(increment -> {
            this.timer = new TimerFactoryImpl().incrementableTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds(), increment);
        }, () -> {
            this.timer = new TimerFactoryImpl().equalTimer(List.of(this.whitePlayer, this.blackPlayer),
                    timer.getSeconds());
        });
    }

    @Override
    public Optional<String> getSettedGameTypeName() {
        if (Optional.ofNullable(this.selectedType).isPresent()) {
            return Optional.ofNullable(this.selectedType.toString());
        }
        if (Optional.ofNullable(this.dynamicGameType).isPresent()) {
            return Optional.ofNullable(this.dynamicGameType.getGameName());
        }
        return Optional.empty();
    }

    @Override
    public boolean isDynamicGameTypeSet() {
        return Optional.ofNullable(this.customizedStartingBoard).isPresent();
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
    public Optional<Player> getWhitePlayer() {
        return Optional.ofNullable(this.whitePlayer);
    }

    @Override
    public Optional<Player> getBlackPlayer() {
        return Optional.ofNullable(this.blackPlayer);
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
    public void clearMatchInfo() {
        this.customizedStartingBoard = null;
        this.selectedType = null;
        this.dynamicGameType = null;
    }

}
