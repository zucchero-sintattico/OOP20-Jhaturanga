package jhaturanga.model.piece.movement;

import java.util.Set;

import jhaturanga.model.piece.Piece;

public final class NormalPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    @Override
    public PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

}
