package jhaturanga.model.player;

import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.user.User;

public class PlayerImpl implements Player {

    /**
     * 
     */
    private static final long serialVersionUID = -294893272595766580L;
    private final PlayerColor color;
    private final User user;
    private final PieceFactoryImpl pieceFactory;

    public PlayerImpl(final PlayerColor color, final User user) {
        this.color = color;
        this.pieceFactory = new PieceFactoryImpl(this);
        this.user = user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final User getUser() {
        return this.user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PlayerColor getColor() {
        return this.color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PieceFactory getPieceFactory() {
        return this.pieceFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getUsername() {
        return this.user.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return "PlayerImpl [color=" + color + ", user=" + this.user + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
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

        return color == other.color;
    }

}
