package jhaturanga.commons.settings.media.style.application;

import java.nio.file.Path;
import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.ApplicationStyleListStrategy;

public final class ApplicationStyle {

    private static ApplicationStyleListStrategy applicationStyleList = new ApplicationStyleListStrategy();
    private static String currentStyle = applicationStyleList.getAllName().get(0);

    private ApplicationStyle() {

    }

    /**
     * set application style.
     * 
     * @param style witch want set
     */
    public static void setApplicationStyle(final String style) {
        if (applicationStyleList.getAllName().contains(style)) {
            currentStyle = style;
        }
    }

    /**
     * get previously set or default application style.
     * 
     * @return piece style
     */
    public static String getApplicationStyle() {
        return currentStyle;
    }

    /**
     * get path witch previously set or default application style.
     * 
     * @return path of current application style
     */
    public static Path getCurrentApplicationStylePath() {
        return getApplicationStylePath(currentStyle).get();
    }

    /**
     * Get path of selected style.
     * 
     * @param style witch want have path
     * @return the path of the selection style
     */
    public static Optional<Path> getApplicationStylePath(final String style) {
        if (applicationStyleList.getAllName().contains(style)) {
            return Optional.of(Path.of(applicationStyleList.getFolderPath().toString().concat("/").concat(style)));
        }
        return Optional.empty();
    }

}
