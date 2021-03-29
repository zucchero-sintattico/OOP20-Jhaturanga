package jhaturanga.commons.style;

import java.io.IOException;

import jhaturanga.commons.datastorage.settings.ApplicationStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.PiecesStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.SettingsDataStorageStrategy;

public final class StyleSettingManager {

    private static SettingsDataStorageStrategy applicationStyle = new ApplicationStyleDateStorageJasonStrategy();
    private static SettingsDataStorageStrategy pieceStyle = new PiecesStyleDateStorageJasonStrategy();

    /**
     * this class is used to communicate the configuration files with the classes
     * that manage the style of the application.
     */
    private StyleSettingManager() {
    }

    /*
     * saves the application style from the configuration file and temporarily sets
     * it in the application style management class
     */
    public static void setAndSaveApplicationStyle(final ApplicationStyleEnum style) throws IOException {
        ApplicationStyle.setApplicationStyle(style);
        applicationStyle.setSetting(style.toString());
    }

    /*
     * saves the piece style from the configuration file and temporarily sets it in
     * the piece style management class.
     */
    public static void setAndSavePieceStyle(final PieceStyleEnum style) throws IOException {
        PieceStyle.setPieceStyle(style);
        pieceStyle.setSetting(style.toString());
    }

    /**
     * get application style saved in the configuration files.
     * 
     * @return application style saved in the configuration files
     * @throws IOException
     */
    public static ApplicationStyleEnum getSavedApplicatioStyle() throws IOException {
        if (applicationStyle.getSetting().isEmpty()) {
            setAndSaveApplicationStyle(ApplicationStyle.getApplicationStyle());
        }

        return ApplicationStyleEnum.valueOf(applicationStyle.getSetting().get());
    }

    /**
     * get piece style saved in the configuration files.
     * 
     * @return piece style saved in the configuration files
     * @throws IOException
     */
    public static PieceStyleEnum getSavedPieceStyle() throws IOException {
        if (pieceStyle.getSetting().isEmpty()) {
            setAndSavePieceStyle(PieceStyle.getPieceStyle());
        }

        return PieceStyleEnum.valueOf(pieceStyle.getSetting().get());
    }

}
