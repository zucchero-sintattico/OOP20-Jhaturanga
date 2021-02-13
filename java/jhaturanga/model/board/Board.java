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
     * Given a BoardPosition it let's you remove a Piece at that position.
     * 
     * @param positionToRemove
     * @return true if the position had a Piece in it and was then removed.
     */
    boolean removeAtPosition(BoardPosition positionToRemove);

    /**
     * Given a Piece it let's you remove it from the Board.
     * 
     * @param pieceToRemove
     * @return true if the Piece is in the Board and was then removed.
     */
    boolean remove(Piece pieceToRemove);

    /**
     * Given a Piece it let's you add a Piece at its position.
     * 
     * @param pieceToAdd
     * @return true if the position didn't have a Piece in it and the Piece given as
     *         parameter was then added.
     */
    boolean add(Piece pieceToAdd);

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
