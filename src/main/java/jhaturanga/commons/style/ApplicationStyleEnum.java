package jhaturanga.commons.style;

public enum ApplicationStyleEnum {
    /**
     * light style.
     */
    LIGHT("css/light.css"),
    /**
     * dark style.
     */
    DARK("css/dark.css");

    private final String path;

    ApplicationStyleEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
