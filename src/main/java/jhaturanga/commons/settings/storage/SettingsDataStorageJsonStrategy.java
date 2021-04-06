package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

public interface SettingsDataStorageJsonStrategy<T> {

    /**
     * 
     * @param value to assign at parameter
     */
    void setSetting(T value) throws IOException;

    /**
     * 
     * @return the value of the selected parameter -OptionalOfNullable if parameter
     *         doesn't exist
     */
    Optional<T> getSetting() throws IOException;
}
