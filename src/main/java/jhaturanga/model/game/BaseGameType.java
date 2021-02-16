package jhaturanga.model.game;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;

public class BaseGameType implements GameType {

    private final Board startingBoard;
    private final PieceMovementStrategyFactory pieceMovementStrategyFactory;
    private final GameController gameController;
    private final MovementManager movementManager;
    private final String gameTypeName;

    public BaseGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory, final Board board,
            final GameController gameController, final ClassicMovementManager movementManager, final String gameTypeName) {
        this.startingBoard = board;
        this.pieceMovementStrategyFactory = pieceMovementStrategyFactory;
        this.gameController = gameController;
        this.movementManager = movementManager;
        this.gameTypeName = gameTypeName;
    }

    @Override
    public String getGameName() {
        return this.gameTypeName;
    }

    @Override
    public GameController getGameController() {
        return this.gameController;
    }

    @Override
    public Board getStartingBoard() {
        return this.startingBoard;
    }

    @Override
    public PieceMovementStrategyFactory getPieceMovementStrategyFactory() {
        return this.pieceMovementStrategyFactory;
    }

    @Override
    public MovementManager getMovementManager() {
        return this.movementManager;
    }

}
