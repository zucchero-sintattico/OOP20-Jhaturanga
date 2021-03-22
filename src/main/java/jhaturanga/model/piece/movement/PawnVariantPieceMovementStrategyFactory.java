package jhaturanga.model.piece.movement;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public class PawnVariantPieceMovementStrategyFactory extends ClassicPieceMovementStrategyFactory {

    /*
     * The increment of the piece. The white goes from bottom to up so the row is
     * incremented by 1 The black goes from top to bottom so the row is incremented
     * by -1
     */
    @Override
    protected final PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        return (board) -> {
            final int increment = piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? SINGLE_INCREMENT
                    : -SINGLE_INCREMENT;

            final Predicate<BoardPosition> checkDirectionAndDistance = (
                    pos) -> Math.signum((pos.getY() - piece.getPiecePosition().getY()) * increment) >= 0
                            && super.distanceBetweenBoardPositions(pos, piece.getPiecePosition())
                                    .getX() <= SINGLE_INCREMENT
                            && super.distanceBetweenBoardPositions(pos, piece.getPiecePosition())
                                    .getY() <= SINGLE_INCREMENT;

            return Stream.concat(
                    super.getRookMovementStrategy(piece).getPossibleMoves(board).stream()
                            .filter(checkDirectionAndDistance),
                    super.getBishopMovementStrategy(piece).getPossibleMoves(board).stream()
                            .filter(checkDirectionAndDistance))
                    .collect(Collectors.toSet());
        };
    }
}
