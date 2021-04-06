package jhaturanga.model.piece.movement;

import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;

@FunctionalInterface
public interface MovementStrategy {

    /**
     * 
     * @param actualBoard - the actual board to check where the piece can go
     * @return a set of all possible position where the piece can go
     * 
     */
    Set<BoardPosition> getPossibleMoves(Board actualBoard);
}
