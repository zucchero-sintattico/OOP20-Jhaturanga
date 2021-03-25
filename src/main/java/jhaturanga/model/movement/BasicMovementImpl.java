package jhaturanga.model.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;

public class BasicMovementImpl implements BasicMovement {

    private final BoardPosition destination;
    private final BoardPosition origin;

    public BasicMovementImpl(final BoardPosition origin, final BoardPosition destination) {
        this.destination = new BoardPositionImpl(destination);
        this.origin = new BoardPositionImpl(origin);
    }

    @Override
    public final BoardPosition getDestination() {
        return this.destination;
    }

    @Override
    public final BoardPosition getOrigin() {
        return this.origin;
    }

    @Override
    public final String toString() {
        return "MovementImpl [" + "origin= " + this.origin + " ,destination=" + destination + "]";
    }
}
