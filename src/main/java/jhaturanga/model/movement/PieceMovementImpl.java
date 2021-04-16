package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public class PieceMovementImpl implements PieceMovement {

    private static final long serialVersionUID = 1018763736571721881L;
    private final Piece pieceInvolved;
    private final BoardPosition destination;
    private final BoardPosition origin;

    public PieceMovementImpl(final Piece piece, final BoardPosition destination) {
        this(piece, new BoardPositionImpl(piece.getPiecePosition()), destination);
    }

    public PieceMovementImpl(final Piece piece, final BoardPosition origin, final BoardPosition destination) {
        this.pieceInvolved = piece;
        this.destination = new BoardPositionImpl(destination);
        this.origin = new BoardPositionImpl(origin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Piece getPieceInvolved() {
        return this.pieceInvolved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BoardPosition getDestination() {
        return this.destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void execute() {
        this.pieceInvolved.setPosition(this.destination);
        this.pieceInvolved.setHasMoved(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BoardPosition getOrigin() {
        return this.origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return "MovementImpl [pieceInvolved=" + pieceInvolved + ", destination=" + destination + ", origin=" + origin
                + "]";
    }

}
