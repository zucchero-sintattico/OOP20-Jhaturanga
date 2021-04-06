package jhaturanga.model.movement;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;

public interface BasicMovement extends Serializable {

    /**
     * @return the position of the wanted destination for the Piece
     */
    BoardPosition getDestination();

    /**
     * @return the position of the starting position of the Piece in this movement
     */
    BoardPosition getOrigin();
}
