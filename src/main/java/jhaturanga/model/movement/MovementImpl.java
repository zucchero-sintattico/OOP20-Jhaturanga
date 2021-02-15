package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public class MovementImpl implements Movement {

    private final Piece pieceInvolved;
    private final BoardPosition destination;
    private final BoardPosition origin;

    public MovementImpl(final Piece piece, final BoardPosition destination) {
        this(piece, new BoardPositionImpl(piece.getPiecePosition()), destination);
    }

    public MovementImpl(final Piece piece, final BoardPosition origin, final BoardPosition destination) {
        this.pieceInvolved = piece;
        this.destination = destination;
        this.origin = origin;
    }

    @Override
    public final Piece getPieceInvolved() {
        return this.pieceInvolved;
    }

    @Override
    public final BoardPosition getDestination() {
        return this.destination;
    }

    @Override
    public final void execute() {
        this.pieceInvolved.setPosition(this.destination);
    }

    @Override
    public final BoardPosition getOrigin() {
        return this.origin;
    }

    @Override
    public final String toString() {
        return "MovementImpl [pieceInvolved=" + pieceInvolved + ", destination=" + destination + ", origin=" + origin
                + "]";
    }

}
