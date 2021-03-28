package jhaturanga.commons.network;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;

public final class NetworkMovement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8947300036883698860L;

    private final BoardPosition origin;
    private final BoardPosition destination;

    public NetworkMovement(final BoardPosition origin, final BoardPosition destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public BoardPosition getOrigin() {
        return this.origin;
    }

    public BoardPosition getDestination() {
        return this.destination;
    }
}
