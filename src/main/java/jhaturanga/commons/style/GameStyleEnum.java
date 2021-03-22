package jhaturanga.commons.style;

public enum GameStyleEnum {

    /**
     * light style.
     */
    CLASSIC("piece/SVG_No_shadow"),
    /**
     * dark style.
     */
    NO_CLASSIC("piece/SVG_with_shadow");

    private final String path;

    GameStyleEnum(final String piecePath) {
        this.path = piecePath;
    }

    public String getPath() {
        return this.path;
    }

}
