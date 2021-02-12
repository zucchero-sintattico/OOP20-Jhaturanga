package jhaturanga.model.player;

import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;

public class PlayerImpl implements Player {

    private final PlayerColor color;
    private final PieceFactoryImpl pieceFactory;

    public PlayerImpl(final PlayerColor color) {
        this.color = color;
        this.pieceFactory = new PieceFactoryImpl(this);
    }

    @Override
    public final PlayerColor getColor() {
        return this.color;
    }

    @Override
    public final PieceFactory getPieceFactory() {
        return this.pieceFactory;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((pieceFactory == null) ? 0 : pieceFactory.hashCode());
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
        if (color != other.color) {
            return false;
        }
        if (pieceFactory == null) {
            if (other.pieceFactory != null) {
                return false;
            }
        } else if (!pieceFactory.equals(other.pieceFactory)) {
            return false;
        }
        return true;
    }

}
