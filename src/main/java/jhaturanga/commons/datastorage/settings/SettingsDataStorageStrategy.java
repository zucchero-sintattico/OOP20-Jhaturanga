package jhaturanga.commons.datastorage.settings;

import java.io.IOException;

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
    String getSetting() throws IOException;
}
