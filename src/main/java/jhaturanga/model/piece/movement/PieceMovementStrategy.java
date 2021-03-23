package jhaturanga.model.piece.movement;

import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

@FunctionalInterface
public interface PieceMovementStrategy {

    /**
     * 
     * @param actualBoard - the actual board to check where the piece can go
     * @param piece       - the piece on which to calculate the current Strategy.
     * @return a set of all possible position where the piece can go
     * 
     */
    Set<BoardPosition> getPossibleMoves(Board actualBoard, Piece piece);
}
