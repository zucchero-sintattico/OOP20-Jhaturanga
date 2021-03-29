package jhaturanga.commons.style;

public final class ApplicationStyle {

    private static ApplicationStyleEnum currentStyle = ApplicationStyleEnum.DARK;

    private ApplicationStyle() {
    }

    /**
     * set application style.
     * 
     * @param style witch want set
     */
    public static void setApplicationStyle(final ApplicationStyleEnum style) {
        currentStyle = style;
    }

    /**
     * get previously set or default application style.
     * 
     * @return piece style
     */
    public static ApplicationStyleEnum getApplicationStyle() {
        return currentStyle;
    }

    /**
     * get path witch previously set or default application style.
     * 
     * @return path of current application style
     */
    public static String getApplicationStylePath() {
        return getApplicationStylePath(currentStyle);
    }

    /**
     * Get path of selected style.
     * 
     * @param style witch want have path
     * @return the path of the selection style
     */
    public static String getApplicationStylePath(final ApplicationStyleEnum style) {
        return style.getPath();
    }

}
