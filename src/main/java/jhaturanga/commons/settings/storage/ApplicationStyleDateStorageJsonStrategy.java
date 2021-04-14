package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public final class ApplicationStyleDateStorageJsonStrategy
        extends SettingDataStorageJson<ApplicationStyleConfigurationObjectStrategy> {

    @Override
    public void setSetting(final ApplicationStyleConfigurationObjectStrategy value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value.getFileName());

    }

    @Override
    public Optional<ApplicationStyleConfigurationObjectStrategy> getSetting() throws IOException {
//        final String savedStyle = this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE);
//        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
//        if (myApplicationStyleList.getAll().stream().filter(e -> e.getFileName().contentEquals(savedStyle))
//                .count() > 0) {
//            return Optional.of(new ApplicationStyleConfigurationObjectStrategy(savedStyle));
//        }
//        return Optional.empty();
        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
        final String savedStyle = this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE);
        return Optional.ofNullable(myApplicationStyleList.getAll().stream()
                .filter(e -> e.getFileName().contentEquals(savedStyle)).findAny().get());

    }

}

//this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE)
