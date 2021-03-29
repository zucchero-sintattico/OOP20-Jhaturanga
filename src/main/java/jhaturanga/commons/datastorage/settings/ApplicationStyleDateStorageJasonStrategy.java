package jhaturanga.commons.datastorage.settings;

import java.io.IOException;
import java.util.Optional;

public final class ApplicationStyleDateStorageJasonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageStrategy {

    @Override
    public void setSetting(final String value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value);

    }

    @Override
    public Optional<String> getSetting() throws IOException {
        return Optional.ofNullable(this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE));
    }

}
