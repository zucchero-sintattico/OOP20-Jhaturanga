package jhaturanga.commons;

/*
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented. 
 */
public class Pair<X, Y> {

    private final X x;
    private final Y y;

    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Get the first value of the pair.
     * 
     * @return the X value of the pair
     */
    public final X getX() {
        return x;
    }

    /**
     * Get the second value of the pair.
     * 
     * @return the Y value of the pair
     */
    public final Y getY() {
        return y;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (!x.equals(other.x)) {
            return false;
        }
        if (y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (!y.equals(other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }

}
