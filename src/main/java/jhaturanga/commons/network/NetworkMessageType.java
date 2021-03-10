package jhaturanga.commons.network;

public enum NetworkMessageType {
    /**
     * Join a game.
     */
    JOIN,

    /**
     * Send a move.
     */
    MOVE;

    public static NetworkMessageType fromString(final String s) {
        switch (s) {

        case "JOIN":
            return JOIN;
        case "MOVE":
            return MOVE;

        default:
            break;
        }

        return null;
    }
}
