package jhaturanga.commons.style;

public final class ApplicationStyle {

    private static ApplicationStyleEnum currentStyle = ApplicationStyleEnum.DARK;

    private ApplicationStyle() {
    }

    /**
     * 
     * @param style witch want set
     */
    public static void setApplicationStyle(final ApplicationStyleEnum style) {
        currentStyle = style;
    }

    /**
     * 
     * @return current application style
     */
    public static ApplicationStyleEnum getApplicationStyle() {
        return currentStyle;
    }

    /**
     * 
     * @return path of current application style
     */
    public static String getApplicationStylePath() {
        return currentStyle.getPath();
    }

    /**
     * 
     * @param style witch want have path
     * @return the path of the selection style
     */
    public static String getApplicationStylePath(final ApplicationStyleEnum style) {
        return style.getPath();
    }

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
}
