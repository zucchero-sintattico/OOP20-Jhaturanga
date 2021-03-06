package jhaturanga.model.piece;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

public class PieceImpl implements Piece {

    private final PieceType pieceType;
    private BoardPosition pieceActualBoardPosition;
    private final Player piecePlayerOwner;
    private boolean moved;

    public PieceImpl(final PieceType pieceType, final BoardPosition pieceActualBoardPosition,
            final Player piecePlayerOwner) {
        this.pieceType = pieceType;
        this.pieceActualBoardPosition = pieceActualBoardPosition;
        this.piecePlayerOwner = piecePlayerOwner;
    }

    @Override
    public final PieceType getType() {
        return this.pieceType;
    }

    @Override
    public final String getIdentifier() {
        return this.pieceType.toString() + "-" + this.getPlayer().toString() + "-" + this.getPiecePosition().toString();
    }

    @Override
    public final void setPosition(final BoardPosition positionalDestination) {
        this.pieceActualBoardPosition = positionalDestination;
    }

    @Override
    public final BoardPosition getPiecePosition() {
        return this.pieceActualBoardPosition;
    }

    @Override
    public final Player getPlayer() {
        return this.piecePlayerOwner;
    }

    public final String toString() {
        return "PieceImpl [" + getIdentifier() + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pieceActualBoardPosition == null) ? 0 : pieceActualBoardPosition.hashCode());
        result = prime * result + ((piecePlayerOwner == null) ? 0 : piecePlayerOwner.hashCode());
        result = prime * result + ((pieceType == null) ? 0 : pieceType.hashCode());
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
        final PieceImpl other = (PieceImpl) obj;
        if (pieceActualBoardPosition == null) {
            if (other.pieceActualBoardPosition != null) {
                return false;
            }
        } else if (!pieceActualBoardPosition.equals(other.pieceActualBoardPosition)) {
            return false;
        }
        if (piecePlayerOwner == null) {
            if (other.piecePlayerOwner != null) {
                return false;
            }
        } else if (!piecePlayerOwner.equals(other.piecePlayerOwner)) {
            return false;
        }
        return pieceType == other.pieceType;
    }

    @Override
    public final boolean hasAlreadyBeenMoved() {
        return this.moved;
    }

    @Override
    public final void hasMoved(final boolean moved) {
        this.moved = true;
    }

}
