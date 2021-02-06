package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public class MovementImpl implements Movement {

    private final Piece pieceInvolved;
    private final BoardPosition destination;

    public MovementImpl(final Piece pieceInvolved, final BoardPosition destination) {
        this.pieceInvolved = pieceInvolved;
        this.destination = destination;
    }

    @Override
    public final Piece getPieceInvolved() {
        return pieceInvolved;
    }

    @Override
    public final BoardPosition getDestination() {
        return this.destination;
    }

    @Override
    public final void execute() {
        this.pieceInvolved.setPosition(this.destination);
    }

}
