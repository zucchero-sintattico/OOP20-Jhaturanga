package jhaturanga.controllers.match;

public enum MovementResult {
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
     * An action that result in and invalid move.
     */
    INVALID_MOVE;
}
