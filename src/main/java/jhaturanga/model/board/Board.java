package jhaturanga.model.board;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.piece.Piece;

/**
 * The interface of the chess Board. *
 */
public interface Board extends Serializable {

    /**
     * Get all the piece on the board.
     * 
     * @return the pieces
     */
    Set<Piece> getPieces();

    /**
     * Get the piece in the specified position.
     * 
     * @param boardPosition - the position to check
     * @return the piece in the specified position if present, Optional.empty
     *         otherwise
     */
    Optional<Piece> getPieceAtPosition(BoardPosition boardPosition);

    /**
     * Given a BoardPosition it let's you know if it is contained or not in the
     * Board currently used.
     * 
     * @param positionToCheck
     * @return true if the position is contained, false otherwise.
     */
    boolean contains(BoardPosition positionToCheck);

    /**
     * Given a Piece it let's you know if it is contained or not in the Board.
     * 
     * @param pieceToCheck - the piece to check if present on the board.
     * @return true if the piece is contained, false otherwise.
     */
    boolean contains(Piece pieceToCheck);

    /**
     * Given a BoardPosition it let's you remove a Piece at that position.
     * 
     * @param positionToRemove - the position where to remove a piece if present.
     * @return true if the position had a Piece in it and was then removed, false if
     *         the piece is not present
     */
    boolean removeAtPosition(BoardPosition positionToRemove);

    /**
     * Given a Piece it let's you remove it from the Board.
     * 
     * @param pieceToRemove - the piece we want to remove.
     * @return true if the Piece is in the Board and was then removed, false
     *         otherwise.
     */
    boolean remove(Piece pieceToRemove);

    /**
     * Add a piece to the board.
     * 
     * @param pieceToAdd - the piece to be added.
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
