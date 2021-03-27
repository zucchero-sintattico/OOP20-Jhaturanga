package jhaturanga.commons.datastorage.settings;

public final class ApplicationStyleDateStorageJasonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageStrategy {

    @Override
    public void setSetting(final String value) {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value);

    }

    @Override
    public String getSetting() {
        return this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE);
    }

}
