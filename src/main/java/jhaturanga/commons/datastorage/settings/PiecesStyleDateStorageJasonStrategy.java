package jhaturanga.commons.datastorage.settings;

import java.io.IOException;
import java.util.Optional;

public final class PiecesStyleDateStorageJasonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageStrategy {

    @Override
    public void setSetting(final String value) throws IOException {
        this.put(SettingTypeEnum.PIECES_STYLE, value);

    }

    @Override
    public Optional<String> getSetting() throws IOException {
        return Optional.ofNullable(this.getSettingValue(SettingTypeEnum.PIECES_STYLE));
    }

}
