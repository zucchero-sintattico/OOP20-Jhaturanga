package jhaturanga.commons.style;

import java.io.IOException;

import jhaturanga.commons.datastorage.settings.ApplicationStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.PiecesStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.SettingsDataStorageStrategy;

public final class StyleSettingManager {

    private static SettingsDataStorageStrategy applicationStyle = new ApplicationStyleDateStorageJasonStrategy();
    private static SettingsDataStorageStrategy pieceStyle = new PiecesStyleDateStorageJasonStrategy();

    private StyleSettingManager() {
    }

    // TODO : tommaso documenta.

    public static void setAndSaveApplicationStyle(final ApplicationStyleEnum style) throws IOException {
        ApplicationStyle.setApplicationStyle(style);
        applicationStyle.setSetting(style.toString());
    }

    public static void setAndSavePieceStyle(final PieceStyleEnum style) throws IOException {
        PieceStyle.setPieceStyle(style);
        pieceStyle.setSetting(style.toString());
    }

    public static ApplicationStyleEnum savedApplicatioStyle() throws IOException {
        if (applicationStyle.getSetting() == null) {
            setAndSaveApplicationStyle(ApplicationStyle.getApplicationStyle());
        }

        return ApplicationStyleEnum.valueOf(applicationStyle.getSetting());
    }

    public static PieceStyleEnum savedPieceStyle() throws IOException {
        if (pieceStyle.getSetting() == null) {
            setAndSavePieceStyle(PieceStyle.getPieceStyle());
        }

        return PieceStyleEnum.valueOf(applicationStyle.getSetting());
    }

}
