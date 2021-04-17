package jhaturanga.model.player;

import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.user.User;

public final class PlayerImpl implements Player {

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
    public User getUser() {
        return this.user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerColor getColor() {
        return this.color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PieceFactory getPieceFactory() {
        return this.pieceFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PlayerImpl [color=" + color + ", user=" + this.user + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
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
        final PlayerImpl other = (PlayerImpl) obj;
        return color == other.color;
    }
}
