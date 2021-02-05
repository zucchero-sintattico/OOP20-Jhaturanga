package jhaturanga.model.board;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.piece.Piece;

public interface Board {

    /**
     * 
     * @return the state of the Match's Board as a set of Piece's
     * 
     */
    Set<Piece> getBoardState();

    /**
     * Get the piece in the specified position.
     * 
     * @param boardPosition - the position to check
     * @return the piece in the specified position
     */
    Optional<Piece> getPieceAtPosition(BoardPosition boardPosition);
}
