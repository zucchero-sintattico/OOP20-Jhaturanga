package jhaturanga.model.piece.movement;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;

public enum Directions {
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a vertical
     * direction, positive or negative depending on the Side passed.
     */
    VERTICAL((pos, side) -> (p) -> new BoardPositionImpl(pos.getX(), pos.getY() + side.getSide())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a vertical
     * direction, positive or negative depending on the Side passed.
     */
    HORIZONTAL((pos, side) -> (p) -> new BoardPositionImpl(pos.getX() + side.getSide(), pos.getY())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a diagonal
     * direction, this is the diagonal that goes from top left towards bottom right,
     * you can choose if to get a direction towards the top left or towards the
     * bottom right depending on the side passed.
     */
    DIAG_TOP_LEFT_BOT_RIGHT(
            (pos, side) -> (p) -> new BoardPositionImpl(pos.getX() - side.getSide(), pos.getY() + side.getSide())),
    /**
     * Use this Direction to get a UnaryOperator of BoardPosition in a diagonal
     * direction, this is the diagonal that goes from bottom left towards top right,
     * you can choose if to get a direction towards the bottom left or towards the
     * top right depending on the side passed.
     */
    DIAG_BOT_LEFT_TOP_RIGHT(
            (pos, side) -> (p) -> new BoardPositionImpl(pos.getX() + side.getSide(), pos.getY() - side.getSide()));

    private final BiFunction<BoardPosition, Side, UnaryOperator<BoardPosition>> direction;

    Directions(final BiFunction<BoardPosition, Side, UnaryOperator<BoardPosition>> direction) {
        this.direction = direction;
    }

    public UnaryOperator<BoardPosition> getDirectionOperator(final BoardPosition pos, final Side side) {
        return this.direction.apply(pos, side);
    }
}
