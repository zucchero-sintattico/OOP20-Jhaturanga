package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.ApplicationStyleConfigObjectStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public final class ApplicationStyleDateStorageJsonStrategy
        extends SettingDataStorageJson<ApplicationStyleConfigObjectStrategy> {

    @Override
    public void setSetting(final ApplicationStyleConfigObjectStrategy value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value.getFileName());

    }

    @Override
    public Optional<ApplicationStyleConfigObjectStrategy> getSetting() throws IOException {

        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
        final String savedStyle = this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE);
        return Optional.ofNullable(savedStyle).map(e -> myApplicationStyleList.getAll().stream()
                .filter(elem -> elem.getFileName().contentEquals(savedStyle)).findAny().get());

    }

}

//this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE)
