package jhaturanga.model.piece.movement;

import java.util.Set;

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
	    return null;
	}
	
    }

    @Override
    public PieceMovementStrategy getPawnMovementStrategy(BoardPosition boardPosition) {
	return (board) -> {
	    return Set.of();
	};
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(BoardPosition boardPosition) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PieceMovementStrategy getKnightMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PieceMovementStrategy getBishopMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PieceMovementStrategy getQueenMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PieceMovementStrategy getKingMovementStrategy(BoardPosition piecePosition) {
	// TODO Auto-generated method stub
	return null;
    }

}
