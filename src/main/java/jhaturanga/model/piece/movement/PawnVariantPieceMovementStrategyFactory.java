package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public class PawnVariantPieceMovementStrategyFactory extends ClassicPieceMovementStrategyFactory {

    @Override
    public final PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        return (final Board board) -> {

            final Set<BoardPosition> positions = new HashSet<>();
            /*
             * The increment of the piece. The white goes from bottom to up so the row is
             * incremented by 1 The black goes from top to bottom so the row is incremented
             * by -1
             */
            final int increment = piece.getPlayer().getColor().equals(PlayerColor.WHITE)
                    ? AbstractPieceMovementStrategyFactory.SINGLE_INCREMENT
                    : -AbstractPieceMovementStrategyFactory.SINGLE_INCREMENT;

            positions.addAll(super.getRookMovementStrategy(piece).getPossibleMoves(board).stream()
                    .filter(i -> Math.signum((i.getY() - piece.getPiecePosition().getY()) * increment) >= 0)
                    .collect(Collectors.toSet()));

            positions.addAll(super.getBishopMovementStrategy(piece).getPossibleMoves(board).stream()
                    .filter(i -> Math.signum((i.getY() - piece.getPiecePosition().getY()) * increment) >= 0)
                    .collect(Collectors.toSet()));

            return Collections.unmodifiableSet(positions.stream()
                    .filter(i -> this.distanceBetweenBoardPositions(piece.getPiecePosition(), i)
                            .getX() <= AbstractPieceMovementStrategyFactory.SINGLE_INCREMENT
                            && this.distanceBetweenBoardPositions(piece.getPiecePosition(), i)
                                    .getY() <= AbstractPieceMovementStrategyFactory.SINGLE_INCREMENT)
                    .collect(Collectors.toSet()));
        };
    }
}
