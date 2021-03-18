package jhaturanga.commons.style;

import jhaturanga.pages.Pages;

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
     * @param page - the page to get the style
     * @return path of current application style
     */
    public static String getApplicationStylePath(final Pages page) {
        return getApplicationStylePath(currentStyle, page);
    }

    /**
     * 
     * @param style witch want have path
     * @return the path of the selection style
     */
    public static String getApplicationStylePath(final ApplicationStyleEnum style, final Pages page) {
        return style.getPath() + page.getName() + ".css";
    }

}
