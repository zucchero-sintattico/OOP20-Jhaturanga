package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public class OneDimensionPieceMovementStrategyFactory extends ClassicPieceMovementStrategyFactory {
    /**
     * This method is used to get the movement strategy of a Knight. It's specific
     * of the kind of variant and GameType.
     */
    @Override
    protected PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
        return (board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(DOUBLE_INCREMENT, -DOUBLE_INCREMENT).forEach(y -> {
                positions.addAll(
                        super.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + y), piece, board, 1));
            });
            return Collections.unmodifiableSet(positions);
        };
    }
}
