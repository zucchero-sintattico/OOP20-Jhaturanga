package jhaturanga.model.piece.movement;

import jhaturanga.model.piece.Piece;

public abstract class AbstractPieceMovementStrategyFactory implements PieceMovementStrategyFactory {

    /**
     * 
     */
    @Override
    public PieceMovementStrategy getPieceMovementStrategy(final Piece piece) {
	switch (piece.getType()) {

	case PAWN:
	    return this.getPawnMovementStrategy(piece);
	case ROOK:
	    return this.getRookMovementStrategy(piece);
	case KNIGHT:
	    return this.getKnightMovementStrategy(piece);
	case BISHOP:
	    return this.getBishopMovementStrategy(piece);
	case QUEEN:
	    return this.getQueenMovementStrategy(piece);
	case KING:
	    return this.getKingMovementStrategy(piece);

	default:
	    return null;
	}

    }
}
