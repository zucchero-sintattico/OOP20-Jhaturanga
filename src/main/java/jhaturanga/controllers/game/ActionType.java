package jhaturanga.controllers.game;

public enum ActionType {
    /**
     * An action that results in a simple move.
     */
    MOVE,
    /**
     * An action that results in a capture.
     */
    CAPTURE,
    /**
     * An action that results in a check.
     */
    CHECK,
    /**
     * An action that results in a checkMate.
     */
    CHECKMATE,
    /**
     * An action that does not result in an action.
     */
    NONE;
}
