package jhaturanga.model.piece.movement;

public enum Side {
    /**
     * LEFT DIRECTION.
     */
    LEFT(-1),
    /**
     * RIGHT DIRECTION.
     */
    RIGHT(1),

    /**
     * UP DIRECTION.
     */
    UP(1),
    /**
     * DOWN DIRECTION.
     */
    DOWN(-1);

    private final int side;

    Side(final int side) {
        this.side = side;
    }

    int getSide() {
        return this.side;
    }
}
