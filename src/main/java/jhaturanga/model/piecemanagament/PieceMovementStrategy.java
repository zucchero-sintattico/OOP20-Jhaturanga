package jhaturanga.model.piecemanagament;

import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;

@FunctionalInterface
public interface PieceMovementStrategy {

    /**
     * 
     * @param actualBoard represents the actual situation of the board through a Set
     *                    of Pieces
     * @return a set of BoardPositions of the Piece
     * 
     */
    Set<BoardPosition> getPossibleMoves(Board actualBoard);
}
