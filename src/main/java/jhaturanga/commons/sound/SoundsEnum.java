package jhaturanga.commons.sound;

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

}
