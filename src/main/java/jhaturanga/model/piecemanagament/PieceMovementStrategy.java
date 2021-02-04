package jhaturanga.model.piecemanagament;

import java.util.Set;

import jhaturanga.model.board.Board;

@FunctionalInterface
public interface PieceMovementStrategy {

    /**
     * 
     * @param actualBoard represents the actual situation of the board through a Set
     *                    of Pieces
     * @return a set of Movements of the Piece
     * 
     */
    Set<Movement> getPossibleMoves(Board actualBoard);
}
