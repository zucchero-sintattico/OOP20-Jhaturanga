package jhaturanga.commons.settings.filegetter;

import java.nio.file.Path;
import java.util.List;

import jhaturanga.commons.settings.config.ConfigurationObjectStrategy;


/**
 * The Interface ConfigurationListStrategy.
 */
public interface ConfigurationListStrategy {

    /**
     * Gets the all path.
     *
     * @return a list of path of all file and directory.
     */
    List<Path> getAllPath();

    /**
     * Gets the all Configuration Object.
     *
     * @return a list of all file and directory.
     */
    List<? extends ConfigurationObjectStrategy> getAll();

    /**
     * Gets the folder path.
     *
     * @return selected folder path
     */
    Path getFolderPath();
}
