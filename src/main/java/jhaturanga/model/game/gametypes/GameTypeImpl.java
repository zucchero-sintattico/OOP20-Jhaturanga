package jhaturanga.model.game.gametypes;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.MovementManager;

public final class GameTypeImpl implements GameType {

    private final GameTypesEnum type;
    private final GameController gameController;
    private final MovementManager movementManager;

    public GameTypeImpl(final GameTypesEnum type, final GameController gameController,
            final MovementManager movementManager) {
        this.type = type;
        this.gameController = gameController;
        this.movementManager = movementManager;
    }

    @Override
    public String getGameName() {
        return this.type.getName();
    }

    @Override
    public GameController getGameController() {
        return this.gameController;
    }

    @Override
    public Board getStartingBoard() {
        return this.gameController.boardState();
    }

    @Override
    public MovementManager getMovementManager() {
        return this.movementManager;
    }

    @Override
    public GameTypesEnum getType() {
        return this.type;
    }

}
