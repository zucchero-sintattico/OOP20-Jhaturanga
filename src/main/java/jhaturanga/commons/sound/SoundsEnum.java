package jhaturanga.commons.sound;

import jhaturanga.model.movement.MovementResult;

public enum SoundsEnum {
    /**
     * sound when move a piece.
     */
    MOVE("move"),

    /**
     * sound when check.
     */
    CHECK("check"),

    /**
     * checkMate sound.
     */
    CHECKMATE("game-end"),

    /**
     * capture sound.
     */
    CAPTURE("capture");

    private String fileName;

    SoundsEnum(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static SoundsEnum fromMovementResult(final MovementResult movementResult) {
        switch (movementResult) {
        case CAPTURE:
            return CAPTURE;
        case CHECK:
            return CHECK;
        case CHECKMATE:
            return CHECKMATE;
        case MOVE:
            return MOVE;
        default:
            throw new IllegalArgumentException("Illegal movement to sound conversion");
        }
    }

}
