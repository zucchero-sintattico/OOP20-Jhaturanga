package jhaturanga.commons.settings.media.sound;

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

    private String soundFileName;

    SoundsEnum(final String fileName) {
        this.soundFileName = fileName;
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public static SoundsEnum fromMovementResult(final MovementResult movementResult) {
        switch (movementResult) {
        case CAPTURED:
            return CAPTURE;
        case CHECKED:
            return CHECK;
        case OVER:
            return CHECKMATE;
        case MOVED:
            return MOVE;
        default:
            throw new IllegalArgumentException("Illegal movement to sound conversion");
        }
    }

}
