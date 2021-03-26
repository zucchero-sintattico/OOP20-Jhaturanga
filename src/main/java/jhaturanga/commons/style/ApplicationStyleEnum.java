package jhaturanga.commons.style;

public enum ApplicationStyleEnum {
    /**
     * light style.
     */
    LIGHT("css/themes/light.css"),
    /**
     * dark style.
     */
    DARK("css/themes/dark.css");

    private final String path;

    ApplicationStyleEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
