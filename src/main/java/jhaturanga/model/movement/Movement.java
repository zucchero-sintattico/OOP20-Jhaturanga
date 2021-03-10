package jhaturanga.model.movement;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface Movement extends Serializable {

    /**
     * @return the Piece involved
     */
    Piece getPieceInvolved();

    /**
     * @return the position of the wanted destination for the Piece
     */
    BoardPosition getDestination();

    /**
     * @return the position of the starting position of the Piece in this movement
     */
    BoardPosition getOrigin();

    /**
     * Executes the movement itself.
     *
     */
    void execute();
}
