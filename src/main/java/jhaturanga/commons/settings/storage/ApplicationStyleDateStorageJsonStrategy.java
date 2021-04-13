package jhaturanga.commons.settings.storage;

import java.io.IOException;

import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.ApplicationStyleListStrategy;

public final class ApplicationStyleDateStorageJsonStrategy extends SettingDataStorageJson<String> {

    @Override
    public void setSetting(final String value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value);

    }

    @Override
    public Optional<String> getSetting() throws IOException {
        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
        if (myApplicationStyleList.getAllName().contains(this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE))) {
            return Optional.of(this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE));
        }
        return Optional.empty();

    }

}

//this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE)
