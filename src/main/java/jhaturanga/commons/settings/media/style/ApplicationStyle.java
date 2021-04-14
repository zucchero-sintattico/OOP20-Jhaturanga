package jhaturanga.commons.settings.media.style;

import java.nio.file.Path;
import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public final class ApplicationStyle {

    private static ApplicationStyleListStrategy applicationStyleList = new ApplicationStyleListStrategy();
    private static ApplicationStyleConfigurationObjectStrategy currentStyle = applicationStyleList.getAll().get(0);

    private ApplicationStyle() {

    }

    /**
     * set application style.
     * 
     * @param style witch want set
     */
    public static void setApplicationStyle(final ApplicationStyleConfigurationObjectStrategy style) {
        if (applicationStyleList.getAll().contains(style)) {
            currentStyle = style;
        }
    }

    /**
     * get previously set or default application style.
     * 
     * @return piece style
     */
    public static ApplicationStyleConfigurationObjectStrategy getApplicationStyle() {
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
    public static Optional<Path> getApplicationStylePath(final ApplicationStyleConfigurationObjectStrategy style) {
        if (applicationStyleList.getAll().contains(style)) {
            return Optional.of(style.getFilePath());
        }
        return Optional.empty();
    }

}