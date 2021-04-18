package jhaturanga.commons.settings.storage;

import java.io.IOException;
import java.util.Optional;

public final class SoundDateStorageStrategy extends SettingDataStorageJson<Double> {

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void setSetting(final Double value) throws IOException {
        this.put(SettingTypeEnum.SOUND_VOLUME, value.toString());

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Optional<Double> getSetting() throws IOException {
        return Optional.ofNullable(this.getSettingValue(SettingTypeEnum.SOUND_VOLUME))
                .map(elem -> Double.valueOf(elem));
    }

}
