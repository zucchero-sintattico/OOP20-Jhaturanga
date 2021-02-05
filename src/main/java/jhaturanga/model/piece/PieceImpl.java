package jhaturanga.model.piece;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.player.Player;

public class PieceImpl implements Piece {

    private final String pieceName;
    private BoardPosition pieceActualBoardPosition;
    private final Player piecePlayerOwner;

    public PieceImpl(final String pieceName, final BoardPosition pieceActualBoardPosition,
	    final Player piecePlayerOwner) {
	this.pieceName = pieceName;
	this.pieceActualBoardPosition = pieceActualBoardPosition;
	this.piecePlayerOwner = piecePlayerOwner;
    }

    @Override
    public String getNameType() {
	return this.pieceName;
    }

    @Override
    public String getUniqueName() {
	// TODO 
	return null;
    }

    @Override
    public void setPosition(final BoardPosition positionalDestination) {
	this.pieceActualBoardPosition = positionalDestination;
    }

    @Override
    public BoardPosition getPiecePosition() {
	return this.pieceActualBoardPosition;
    }

    @Override
    public Player getPlayer() {
	return this.piecePlayerOwner;
    }

}
