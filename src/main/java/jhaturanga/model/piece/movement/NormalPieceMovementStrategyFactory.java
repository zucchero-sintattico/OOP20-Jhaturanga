package jhaturanga.model.piece.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public final class NormalPieceMovementStrategyFactory implements PieceMovementStrategyFactory {

    @Override
    public PieceMovementStrategy getPieceMovementStrategy(final Piece piece) {
	switch (piece.getType()) {

	case PAWN:
	    return this.getPawnMovementStrategy(piece.getPiecePosition());
	case ROOK:
	    return this.getRookMovementStrategy(piece.getPiecePosition());
	case KNIGHT:
	    return this.getKnightMovementStrategy(piece.getPiecePosition());
	case BISHOP:
	    return this.getBishopMovementStrategy(piece.getPiecePosition());
	case QUEEN:
	    return this.getQueenMovementStrategy(piece.getPiecePosition());
	case KING:
	    return this.getKingMovementStrategy(piece.getPiecePosition());

	default:
	    break;
	}
	return null;
    }

    /**
     * Get the movement strategy for the pawn.
     * 
     * @param boardPosition - the position of the pawn
     * @return the pawn movement strategy
     */
    private PieceMovementStrategy getPawnMovementStrategy(final BoardPosition boardPosition) {
	return null;
    }

    /**
     * 
     * @param boardPosition
     * @return
     */
    private PieceMovementStrategy getRookMovementStrategy(final BoardPosition boardPosition) {
	return null;
    }

    private PieceMovementStrategy getKnightMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    private PieceMovementStrategy getBishopMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    private PieceMovementStrategy getQueenMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    private PieceMovementStrategy getKingMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

}
