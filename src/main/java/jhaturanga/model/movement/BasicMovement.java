package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;

public interface BasicMovement {
    /**
     * @return the position of the wanted destination for the Piece
     */
    BoardPosition getDestination();

    /**
     * @return the position of the starting position of the Piece in this movement
     */
    BoardPosition getOrigin();
}
