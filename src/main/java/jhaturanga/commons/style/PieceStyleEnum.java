package jhaturanga.commons.style;

public enum PieceStyleEnum {

    /**
     * classic style.
     */
    CLASSIC("piece/PNGs/No_shadow"),
    /**
     * no_classic style.
     */
    NO_CLASSIC("piece/PNGs/With_Shadow");

    private final String path;

    PieceStyleEnum(final String piecePath) {
        this.path = piecePath;
    }

    public String getPath() {
        return this.path;
    }

}