package jhaturanga.model.movement;

import jhaturanga.model.piece.Piece;

public interface Movement extends BasicMovement {

    /**
     * @return the Piece involved
     */
    Piece getPieceInvolved();

    /**
     * Executes the movement itself.
     *
     */
    void execute();
}
