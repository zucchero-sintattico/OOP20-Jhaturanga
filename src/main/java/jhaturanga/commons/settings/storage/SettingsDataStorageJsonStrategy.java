package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

/**
 * The Interface SettingsDataStorageJsonStrategy.
 *
 * @param <T> the parameter witch wont save.
 */
public interface SettingsDataStorageJsonStrategy<T> {

    /**
     * Sets the setting.
     *
     * @param value to assign at parameter
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void setSetting(T value) throws IOException;

    /**
     * Gets the setting.
     *
     * @return the value of the selected parameter -OptionalOfNullable if parameter
     *         doesn't exist
     * @throws IOException Signals that an I/O exception has occurred.
     */
    Optional<T> getSetting() throws IOException;
}
