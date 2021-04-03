package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public class ClassicNoCastlingPieceMovementStrategies extends ClassicWithCastlingPieceMovementStrategies {
    /**
     * This method is used to get the movement strategy of a King. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    protected MovementStrategy getKingMovementStrategy(final Piece piece) {
        return (board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getQueenMovementStrategy(piece).getPossibleMoves(board).stream()
                    .filter(pos -> super.pieceDistanceFromPositionLessThan(piece, pos, SINGLE_INCREMENT))
                    .collect(Collectors.toSet()));
            return Collections.unmodifiableSet(positions);
        };
    }
}
