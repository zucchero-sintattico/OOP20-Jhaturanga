package jhaturanga.model.piece;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

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
     * Used to set the boolean property hasMoved of the Piece.
     * 
     * @param moved sets the boolean property hasMoved
     */
    void hasMoved(boolean moved);

    /**
     * Get if the piece was moved or not.
     * 
     * @return true if the piece was moved, false otherwise
     */
    boolean hasAlreadyBeenMoved();

}
