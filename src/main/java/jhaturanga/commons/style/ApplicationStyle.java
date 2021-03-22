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
     * @return path of current application style
     */
    public static String getApplicationStylePath() {
        return getApplicationStylePath(currentStyle);
    }

    /**
     * 
     * @param style witch want have path
     * @return the path of the selection style
     */
    public static String getApplicationStylePath(final ApplicationStyleEnum style) {
        return style.getPath();
    }

}
