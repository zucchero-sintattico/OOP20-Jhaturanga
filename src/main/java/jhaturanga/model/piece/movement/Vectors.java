package jhaturanga.model.piece.movement;

import java.util.function.Function;

import jhaturanga.commons.Pair;

public enum Vectors {
    /**
     * Represent a vector parallel to the Y axis of the board, because of the use
     * that is made of the vector, the sign of the components is not important in
     * the case of VERTICAL and HORIZONTAL.
     */
    VERTICAL(new Pair<>(0, 1)),
    /**
     * Represent a vector parallel to the X axis of the board, because of the use
     * that is made of the vector, the sign of the components is not important in
     * the case of VERTICAL and HORIZONTAL.
     */
    HORIZONTAL(new Pair<>(1, 0)),
    /**
     * This is a vector that points towards the bottom-left of the board.
     */
    TOP_LEFT_BOT_RIGHT(new Pair<>(1, -1)),
    /**
     * This is a vector that points towards the top-right of the board.
     */
    TOP_RIGHT_BOT_LEFT(new Pair<>(1, 1));

    private Pair<Integer, Integer> axis;
    private Function<Pair<Integer, Integer>, Pair<Integer, Integer>> oppositeAxis = (axis) -> new Pair<>(-axis.getX(),
            -axis.getY());

    Vectors(final Pair<Integer, Integer> axis) {
        this.axis = axis;
    }

    Pair<Integer, Integer> getAxis() {
        return this.axis;
    }

    Pair<Integer, Integer> getOpposite() {
        return this.oppositeAxis.apply(this.getAxis());
    }
}
