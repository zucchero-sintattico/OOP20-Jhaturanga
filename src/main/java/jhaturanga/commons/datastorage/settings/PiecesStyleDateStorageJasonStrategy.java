package jhaturanga.commons.datastorage.settings;

import java.io.IOException;

public final class PiecesStyleDateStorageJasonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageStrategy {

    @Override
    public void setSetting(final String value) throws IOException {
        this.put(SettingTypeEnum.PIECES_STYLE, value);

    }

    @Override
    public String getSetting() throws IOException {
        return this.getSettingValue(SettingTypeEnum.PIECES_STYLE);
    }

}
