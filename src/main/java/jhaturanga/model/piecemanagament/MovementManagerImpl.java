package jhaturanga.model.piecemanagament;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;

public class MovementManagerImpl implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory movementsStrategies;

    public MovementManagerImpl(final Board startingBoard, final PieceMovementStrategyFactory movementsStrategies) {	
	this.movementsStrategies = movementsStrategies;
	this.board = startingBoard;
    }

    @Override
    public boolean move(final Movement movement) {
	return true;
    }

}
