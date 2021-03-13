package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public class NoCastlingPieceMovementStrategyFactory extends ClassicPieceMovementStrategyFactory {
    @Override
    public final PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getQueenMovementStrategy(piece).getPossibleMoves(board).stream().filter(i -> this
                    .distanceBetweenBoardPositions(piece.getPiecePosition(), i).getX() <= SINGLE_INCREMENT
                    && this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getY() <= SINGLE_INCREMENT)
                    .collect(Collectors.toSet()));

            return Collections.unmodifiableSet(positions);
        };
    }
}
