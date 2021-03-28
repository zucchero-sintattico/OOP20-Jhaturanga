package jhaturanga.model.movement;

public enum MovementResult {
    /**
     * An action that results in a simple move.
     */
    MOVED,
    /**
     * An action that results in a capture.
     */
    CAPTURED,
    /**
     * An action that results in a check.
     */
    CHECKED,
    /**
     * An action that results in a checkMate.
     */
    CHECKMATED,
    /**
     * An action that result in and invalid move.
     */
    INVALID_MOVE;
}
