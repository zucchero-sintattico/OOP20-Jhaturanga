package jhaturanga.commons.datastorage.settings;

import java.io.IOException;

import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.commons.style.PieceStyleEnum;

public final class SaveStyleSettings {

    private SaveStyleSettings() {
        // TODO Auto-generated constructor stub
    }

    public static void saveApplicationStyleSetting(final ApplicationStyleEnum style) throws IOException {
        final SettingsDataStorageStrategy styleSet = new ApplicationStyleDateStorageJasonStrategy();
        styleSet.setSetting(style.toString());
    }

    public static void savePieceStyleSetting(final PieceStyleEnum style) throws IOException {
        final SettingsDataStorageStrategy styleSet = new PiecesStyleDateStorageJasonStrategy();
        styleSet.setSetting(style.toString());
    }

}
