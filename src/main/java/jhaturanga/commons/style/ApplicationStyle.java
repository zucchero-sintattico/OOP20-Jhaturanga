package jhaturanga.commons.style;

public final class ApplicationStyle {

    public enum ApplicationStyleEnum {
        LIGHT, DARK
    }

    private static ApplicationStyleEnum currentStyle = ApplicationStyleEnum.LIGHT;

    public static void setApplicationStyle(final ApplicationStyleEnum style) {
        currentStyle = style;

    }

    public ApplicationStyleEnum getApplicationStyle() {
        return currentStyle;
    }

    public static String getApplicationStylePath() {
        // TODO Auto-generated method stub
        return "css/dark.css";
    }

    public String getApplicationStylePath(final ApplicationStyleEnum style) {
        // TODO Auto-generated method stub
        return "css/dark.css";
    }

}
