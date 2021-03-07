package jhaturanga.commons.sound;

public enum SoundsEnum {
    /**
     * sound when move a piece.
     */
    MOVE("move");

    private String fileName;

    SoundsEnum(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
