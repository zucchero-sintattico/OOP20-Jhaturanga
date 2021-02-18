package jhaturanga.commons.network;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;

public final class Movements {

    private Movements() {

    }

    public static Movement decode(final String movement, final Board board) {
        return new MovementImpl(null, null);
    }

    public static String encode(final Movement movement) {
        return "";
    }
}
