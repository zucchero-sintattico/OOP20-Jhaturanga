package jhaturanga.model.piece;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

public interface Piece {

    /**
     * @return the name of the type of this Piece
     */
    String getNameType();

    /**
     * @return the unique name of this piece, it can be seen as an ID for the Piece
     */
    String getUniqueName();

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

}
