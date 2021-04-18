package jhaturanga.model.piece.movement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.piece.Piece;

public class EveryoneMovesLikeRooksPieceMovementStrategies extends AbstractPieceMovementStrategies {

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getPawnMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getRookMovementStrategy(final Piece piece) {
        return (board) -> {
            return Stream
                    .concat(super.getSpecularNoLimitDirection().apply(piece, Vectors.VERTICAL, board).stream(),
                            super.getSpecularNoLimitDirection().apply(piece, Vectors.HORIZONTAL, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getKnightMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getBishopMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getQueenMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MovementStrategy getKingMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

}
