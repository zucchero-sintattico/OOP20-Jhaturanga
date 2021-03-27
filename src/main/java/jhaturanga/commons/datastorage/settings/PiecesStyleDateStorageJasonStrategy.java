package jhaturanga.commons.datastorage.settings;

public final class PiecesStyleDateStorageJasonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageStrategy {

    @Override
    public void setSetting(final String value) {
        this.put(SettingTypeEnum.PIECES_STYLE, value);

    }

    @Override
    public String getSetting() {
        return this.getSettingValue(SettingTypeEnum.PIECES_STYLE);
    }

}
