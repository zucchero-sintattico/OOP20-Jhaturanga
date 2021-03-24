package jhaturanga.model.piece.movement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.piece.Piece;

public class RookAndBishopPieceMovementStrategyFactory extends ClassicPieceMovementStrategyFactory {

    /**
     * This method is used to get the movement strategy of a Rook. It's a Hybrid
     * between a Rook and a Bishop, and it's identical to the Bishop's in this case.
     */
    @Override
    protected PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
        return (board) -> {
            return this.getBishopMovementStrategy(piece).getPossibleMoves(board);
        };
    }

    /**
     * This method is used to get the movement strategy of a Bishop. In this
     * specific MovementStrategy the bishop has a mixed movement, it' a hybrid
     * between a Rook and a Bishop.
     */
    @Override
    protected PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
        return (board) -> {
            return Stream.concat(super.getSpecularNoLimitDirection().apply(piece, Vectors.VERTICAL, board).stream(),
                    super.getSpecularNoLimitDirection().apply(piece, Vectors.TOP_RIGHT_BOT_LEFT, board).stream())
                    .collect(Collectors.toSet());
        };
    }
}
