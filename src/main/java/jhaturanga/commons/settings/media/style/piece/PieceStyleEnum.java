package jhaturanga.commons.settings.media.style.piece;

public enum PieceStyleEnum {

    /**
     * classic style.
     */
    CLASSIC("piece/classic/"),
    /**
     * no_classic style.
     */
    SHADOW("piece/shadow/");

    private final String path;

    PieceStyleEnum(final String piecePath) {
        this.path = piecePath;
    }

    public String getPath() {
        return this.path;
    }

}
