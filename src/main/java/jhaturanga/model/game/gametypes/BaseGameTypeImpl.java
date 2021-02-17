package jhaturanga.model.game.gametypes;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.MovementManager;

public class BaseGameTypeImpl implements GameType {

    private Board startingBoard;
    private GameController gameController;
    private String gameTypeName;
    private MovementManager movementManager;

    @Override
    public final String getGameName() {
        return this.gameTypeName;
    }

    @Override
    public final GameController getGameController() {
        return this.gameController;
    }

    @Override
    public final Board getStartingBoard() {
        return this.startingBoard;
    }

    @Override
    public final MovementManager getMovementManager() {
        return this.movementManager;
    }

    public final String getGameTypeName() {
        return gameTypeName;
    }

    public final void setGameTypeName(final String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }

    public final void setStartingBoard(final Board startingBoard) {
        this.startingBoard = startingBoard;
    }

    public final void setGameController(final GameController gameController) {
        this.gameController = gameController;
    }

    public final void setMovementManager(final MovementManager movementManager) {
        this.movementManager = movementManager;
    }

}
