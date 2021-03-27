package jhaturanga.commons.datastorage.settings;

public interface SettingsDataStorageStrategy {

    /**
     * 
     * @param value you wont set on parameter
     */
    void setSetting(String value);

    /**
     * 
     * @return value of parameter
     */
    String getSetting();
}
