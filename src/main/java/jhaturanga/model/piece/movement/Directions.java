package jhaturanga.model.piece.movement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public enum Directions {
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a vertical
     * direction, positive or negative depending on the Side passed.
     */
    VERTICAL((pos, side) -> (p) -> new BoardPositionImpl(p.getX(), p.getY() + side.getSide())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a vertical
     * direction, positive or negative depending on the Side passed.
     */
    HORIZONTAL((pos, side) -> (p) -> new BoardPositionImpl(p.getX() + side.getSide(), pos.getY())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a diagonal
     * direction, this is the diagonal that goes from top left towards bottom right,
     * you can choose if to get a direction towards the top left or towards the
     * bottom right depending on the side passed.
     */
    DIAG_TOP_LEFT_BOT_RIGHT(
            (pos, side) -> (p) -> new BoardPositionImpl(p.getX() + side.getSide(), p.getY() - side.getSide())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a diagonal
     * direction, this is the diagonal that goes from bottom left towards top right,
     * you can choose if to get a direction towards the bottom left or towards the
     * top right depending on the side passed.
     */
    DIAG_BOT_LEFT_TOP_RIGHT(
            (pos, side) -> (p) -> new BoardPositionImpl(p.getX() + side.getSide(), p.getY() + side.getSide()));

    private final BiFunction<BoardPosition, Side, UnaryOperator<BoardPosition>> direction;

    Directions(final BiFunction<BoardPosition, Side, UnaryOperator<BoardPosition>> direction) {
        this.direction = direction;
    }

    public UnaryOperator<BoardPosition> getDirectionOperator(final BoardPosition pos, final Side side) {
        return this.direction.apply(pos, side);
    }

    private Set<BoardPosition> fromFunction(final UnaryOperator<BoardPosition> function, final Piece piece,
            final Board board, final int limit) {
        /*
         * The "function.apply" at the seed of the Stream.Iterate is used to skip the
         * first element, that's itself.
         */
        final List<BoardPosition> positions = Stream.iterate(function.apply(piece.getPiecePosition()), function)
                .takeWhile(board::contains)
                .takeWhile(x -> board.getPieceAtPosition(x).isEmpty()
                        || !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                .limit(limit).collect(Collectors.toList());

        final Optional<BoardPosition> pos = positions.stream().filter(i -> board.getPieceAtPosition(i).isPresent()
                && !board.getPieceAtPosition(i).get().getPlayer().equals(piece.getPlayer())).findFirst();

        /*
         * The sublist excludes the last n-th element of the high-endpoint
         */
        return pos.isEmpty() ? new HashSet<>(positions)
                : new HashSet<>(positions.subList(0, positions.indexOf(pos.get()) + 1));
    }
}
