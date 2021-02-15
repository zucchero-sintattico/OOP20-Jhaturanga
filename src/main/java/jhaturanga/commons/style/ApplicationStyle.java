package jhaturanga.commons.style;

public final class ApplicationStyle {

    private ApplicationStyle() {
    }

    private static ApplicationStyleEnum currentStyle = ApplicationStyleEnum.LIGHT;

    public static void setApplicationStyle(final ApplicationStyleEnum style) {
        currentStyle = style;
    }

    public ApplicationStyleEnum getApplicationStyle() {
        return currentStyle;
    }

    public static String getApplicationStylePath() {
        return currentStyle.path;
    }

    public static String getApplicationStylePath(final ApplicationStyleEnum style) {
        return style.path;
    }

    enum ApplicationStyleEnum {
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
    }
}​ ​
