package jhaturanga.commons.datastorage.settings;

import java.io.IOException;
import java.util.Optional;

public interface SettingsDataStorageStrategy {

    /**
     * 
     * @param value you wont set on parameter
     */
    void setSetting(String value) throws IOException;

    /**
     * 
     * @return value of parameter
     */
    Optional<String> getSetting() throws IOException;
}
