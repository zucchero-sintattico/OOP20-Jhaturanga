package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface Movement {

    /**
     * @return the Piece involved
     */
    Piece getPieceInvolved();

    /**
     * @return the position of the wanted destination for the Piece
     */
    BoardPosition getDestination();

    /**
     * Executes the movement itself.
     *
     */
    void execute();
}
