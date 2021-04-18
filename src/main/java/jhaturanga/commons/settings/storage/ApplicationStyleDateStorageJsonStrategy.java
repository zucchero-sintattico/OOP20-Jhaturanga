package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.settings.config.ApplicationStyleConfigStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public final class ApplicationStyleDateStorageJsonStrategy
        extends SettingDataStorageJson<ApplicationStyleConfigStrategy> {

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void setSetting(final ApplicationStyleConfigStrategy value) throws IOException {
        this.put(SettingTypeEnum.APPLICATION_STYLE, value.getFileName());

    }
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Optional<ApplicationStyleConfigStrategy> getSetting() throws IOException {

        final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();
        final String savedStyle = this.getSettingValue(SettingTypeEnum.APPLICATION_STYLE);
        return Optional.ofNullable(savedStyle).map(e -> myApplicationStyleList.getAll().stream()
                .filter(elem -> elem.getFileName().contentEquals(savedStyle)).findAny().get());

    }

}


