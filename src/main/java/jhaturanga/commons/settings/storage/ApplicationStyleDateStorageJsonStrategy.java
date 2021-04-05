package jhaturanga.commons.settings.storage;

import java.io.IOException;

import java.util.Optional;

import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;

public final class ApplicationStyleDateStorageJsonStrategy extends SettingDataStorageJson
        implements SettingsDataStorageJsonStrategy<ApplicationStyleEnum> {

    @Override
    public void setSetting(final ApplicationStyleEnum value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value.toString());

    }

    @Override
    public Optional<ApplicationStyleEnum> getSetting() throws IOException {

        return Optional.ofNullable(this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE))
                .map(ApplicationStyleEnum::valueOf);

    }

}

//this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE)
