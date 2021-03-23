package jhaturanga.model.piece.movement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EveryoneMovesLikeRooksPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    @Override
    protected final PieceMovementStrategy getPawnMovementStrategy() {
        return this.getRookMovementStrategy();
    }

    @Override
    protected final PieceMovementStrategy getRookMovementStrategy() {
        return (board, piece) -> {
            return Stream
                    .concat(super.getSpecularNoLimitDirection().apply(piece, Vectors.VERTICAL, board).stream(),
                            super.getSpecularNoLimitDirection().apply(piece, Vectors.HORIZONTAL, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    @Override
    protected final PieceMovementStrategy getKnightMovementStrategy() {
        return this.getRookMovementStrategy();
    }

    @Override
    protected final PieceMovementStrategy getBishopMovementStrategy() {
        return this.getRookMovementStrategy();
    }

    @Override
    protected final PieceMovementStrategy getQueenMovementStrategy() {
        return this.getRookMovementStrategy();
    }

    @Override
    protected final PieceMovementStrategy getKingMovementStrategy() {
        return this.getRookMovementStrategy();
    }

}
