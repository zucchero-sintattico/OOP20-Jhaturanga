package jhaturanga.commons.datastorage.settings;

import java.io.IOException;
import java.util.Optional;

public interface SettingsDataStorageStrategy {

    /**
     * 
     * @param value to assign at parameter
     */
    void setSetting(String value) throws IOException;

    /**
     * 
     * @return the value of the selected parameter -OptionalOfNullable if parameter
     *         doesn't exist
     */
    Optional<String> getSetting() throws IOException;
}
