package jhaturanga.model.player;

public class PlayerImpl implements Player {

    private final PlayerColor color;

    public PlayerImpl(final PlayerColor color) {
        this.color = color;
    }

    @Override
    public final PlayerColor getColor() {
        return this.color;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

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
        final PlayerImpl other = (PlayerImpl) obj;
        return color.equals(other.color);
    }

}
