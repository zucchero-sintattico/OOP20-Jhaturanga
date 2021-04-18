package jhaturanga.model.piece;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

/**
 * The entity for the Piece.
 */
public interface Piece extends Serializable {

    /**
     * @return the name of the type of this Piece
     */
    PieceType getType();

    /**
     * @return the unique name of this piece, it can be seen as an ID for the Piece
     */
    String getIdentifier();

    /**
     * @param positionalDestination is the position to which move the Piece on the
     *                              Board
     * 
     */
    void setPosition(BoardPosition positionalDestination);

    /**
     * @return the actual Piece's position on the board
     * 
     */
    BoardPosition getPiecePosition();

    /**
     * @return the actual Piece's Player owner
     * 
     */
    Player getPlayer();

    /**
     * Get if the piece was moved or not.
     * 
     * @return true if the piece was moved, false otherwise
     */
    boolean hasAlreadyBeenMoved();

    /**
     * Set that the piece was moved.
     * 
     * @param moved - true if the piece was moved, false otherwise
     */
    void setHasMoved(boolean moved);

}
