package jhaturanga.controllers.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameTypeBuilder;
import jhaturanga.model.game.gametypes.GameTypeBuilderImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.movement.NoCastlingMovementManager;
import jhaturanga.model.piece.movement.NoCastlingPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.startingboards.StartingBoardFactoryImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

    @Override
    public void setTimer(final DefaultsTimers timer) {
        this.getModel().setTimer(timer);
    }

    @Override
    public void createMatch() {
        if (this.getModel().getEditor().getCreatedBoard().isPresent()) {
            final GameTypeBuilder gameTypeBuilder = new GameTypeBuilderImpl();
            final GameController gameController = new ClassicGameController(
                    new StartingBoardFactoryImpl().customizedBoard(
                            this.getModel().getEditor().getCreatedBoard().get().getX(),
                            this.getModel().getEditor().getCreatedBoard().get().getY().getX(),
                            this.getModel().getEditor().getCreatedBoard().get().getY().getY(),
                            this.getModel().getWhitePlayer(), this.getModel().getBlackPlayer()),
                    new NoCastlingPieceMovementStrategyFactory(),
                    List.of(this.getModel().getWhitePlayer(), this.getModel().getBlackPlayer()));

            // final GameType gameType = gameTypeBuilder.gameController(new
            // ClassicGameController(board, pieceMovementStrategies, players))
            this.getModel()
                    .setGameType(gameTypeBuilder.gameController(gameController)
                            .movementManager(new NoCastlingMovementManager(gameController))
                            .gameTypeName("Customizable Board Variant").build());

        }
        this.getModel().createMatch();
    }

    @Override
    public Optional<String> getNameGameTypeSelected() {
        if (this.getModel().getGameType().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.getModel().getGameType().get().toString());

        }
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.getModel().setWhitePlayer(player);
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.getModel().setBlackPlayer(player);
    }

    @Override
    public void setFirstUserGuest() {
        this.getModel().setFirstUser(UsersManager.GUEST);

    }

    @Override
    public void setSecondUserGuest() {
        this.getModel().setSecondUser(UsersManager.GUEST);

    }

    @Override
    public User getFirstUser() {
        return this.getModel().getFirstUser().get();
    }

    @Override
    public User getSecondUser() {
        return this.getModel().getSecondUser().get();
    }

    @Override
    public boolean isFirstUserLogged() {
        return this.getModel().getFirstUser().isPresent();
    }

    @Override
    public boolean isSecondUserLogged() {
        return this.getModel().getSecondUser().isPresent();
    }

    @Override
    public List<Board> loadMatch() throws IOException, ClassNotFoundException {

//        final FileInputStream fileIn = new FileInputStream("Test.txt");
//        fileIn.close();
//        final ObjectInputStream objectIn = new ObjectInputStream(fileIn);
//        objectIn.close();
//        final Object obj = objectIn.readObject();
//        return (List<Board>) obj;

        return null;
    }

    @Override
    public void setupPlayers() {
        this.getModel().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, this.getFirstUser()));
        this.getModel().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, this.getSecondUser()));
    }

}
