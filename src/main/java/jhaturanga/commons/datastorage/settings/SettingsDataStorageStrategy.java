package jhaturanga.commons.datastorage.settings;

public interface SettingsDataStorageStrategy {

    enum SettingType {
        APPLICATION_STYLE, PIECES_STYLE
    }

    void setSetting(String value);

    String getSetting();
}
