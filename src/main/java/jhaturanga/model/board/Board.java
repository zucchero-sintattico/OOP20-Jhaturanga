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

    /**
     * Given a BoardPosition it let's you know if it is contained or not in the
     * Board currently used.
     * 
     * @param positionToCheck
     * @return true if the position is contained it otherwise returns false.
     */
    boolean contains(BoardPosition positionToCheck);

    /**
     * Used to get the columns of the Board.
     * 
     * @return the columns of the Board.
     */
    int getColumns();

    /**
     * Used to get the rows of the Board.
     * 
     * @return the rows of the Board.
     */
    int getRows();
}
