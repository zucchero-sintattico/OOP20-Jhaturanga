package jhaturanga.model.board;

public final class BoardPositionImpl implements BoardPosition {

    /**
     * The serial version.
     */
    private static final long serialVersionUID = -7518041140044999585L;
    private final int xPosition;
    private final int yPosition;

    public BoardPositionImpl(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public BoardPositionImpl(final BoardPosition pos) {
        this.xPosition = pos.getX();
        this.yPosition = pos.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return this.xPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        return this.yPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BoardPositionImpl [xPosition=" + xPosition + ", yPosition=" + yPosition + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xPosition;
        result = prime * result + yPosition;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardPositionImpl other = (BoardPositionImpl) obj;
        if (xPosition != other.xPosition) {
            return false;
        }
        return yPosition == other.yPosition;
    }

}
