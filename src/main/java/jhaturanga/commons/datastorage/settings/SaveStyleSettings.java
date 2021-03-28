package jhaturanga.commons.datastorage.settings;

import java.io.IOException;

import jhaturanga.commons.style.ApplicationStyleEnum;

public final class SaveStyleSettings {

    private SaveStyleSettings() {
        // TODO Auto-generated constructor stub
    }

    public static void saveStyleSetting(final ApplicationStyleEnum style) throws IOException {
        final SettingsDataStorageStrategy styleSet = new ApplicationStyleDateStorageJasonStrategy();
        styleSet.setSetting(style.toString());
    }

}
