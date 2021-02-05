package jhaturanga.model.piece;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

public class PieceImpl implements Piece {

    private final PieceType pieceType;
    private BoardPosition pieceActualBoardPosition;
    private final Player piecePlayerOwner;

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
	return this.pieceType.toString() + "-" + this.getPiecePosition().toString();
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

}
