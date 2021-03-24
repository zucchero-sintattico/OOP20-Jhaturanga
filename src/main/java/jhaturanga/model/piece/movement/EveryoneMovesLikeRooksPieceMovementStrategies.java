package jhaturanga.model.piece.movement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.piece.Piece;

public class EveryoneMovesLikeRooksPieceMovementStrategies extends AbstractPieceMovementStrategies {

    @Override
    protected final PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    @Override
    protected final PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
        return (board) -> {
            return Stream
                    .concat(super.getSpecularNoLimitDirection().apply(piece, Vectors.VERTICAL, board).stream(),
                            super.getSpecularNoLimitDirection().apply(piece, Vectors.HORIZONTAL, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    @Override
    protected final PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    @Override
    protected final PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    @Override
    protected final PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

    @Override
    protected final PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
        return this.getRookMovementStrategy(piece);
    }

}
