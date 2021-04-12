package jhaturanga.commons.settings;

import java.io.IOException;

import jhaturanga.commons.settings.media.sound.Sound;
import jhaturanga.commons.settings.media.style.application.ApplicationStyle;
import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;
import jhaturanga.commons.settings.media.style.piece.PieceStyle;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.commons.settings.storage.ApplicationStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.PiecesStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SettingsDataStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SoundDateStorageStrategy;

public final class SettingMediator {

    private static SettingsDataStorageJsonStrategy<ApplicationStyleEnum> applicationStyleJson = new ApplicationStyleDateStorageJsonStrategy();
    private static SettingsDataStorageJsonStrategy<PieceStyleEnum> pieceStyleJson = new PiecesStyleDateStorageJsonStrategy();
    private static SettingsDataStorageJsonStrategy<Double> soundVolumeJson = new SoundDateStorageStrategy();

    /**
     * this class is used to communicate the configuration files with the classes
     * that manage the style of the application.
     */
    private SettingMediator() {
    }

    /*
     * saves the application style from the configuration file and temporarily sets
     * it in the application style management class
     */
    public static void setAndSaveApplicationStyle(final ApplicationStyleEnum style) throws IOException {
        ApplicationStyle.setApplicationStyle(style);
        applicationStyleJson.setSetting(style);
    }

    /*
     * saves the piece style from the configuration file and temporarily sets it in
     * the piece style management class.
     */
    public static void setAndSavePieceStyle(final PieceStyleEnum style) throws IOException {
        PieceStyle.setPieceStyle(style);
        pieceStyleJson.setSetting(style);
    }

    /*
     * saves the volume from the configuration file and temporarily sets it in the
     * sound management class.
     */
    public static void setAndSaveSoundVolume(final double volume) throws IOException {
        Sound.setVolume(volume);
        soundVolumeJson.setSetting(volume);
    }

    /**
     * get application style saved in the configuration files.
     * 
     * @return application style saved in the configuration files
     * @throws IOException
     */
    public static ApplicationStyleEnum getSavedApplicatioStyle() throws IOException {
        if (applicationStyleJson.getSetting().isEmpty()) {
            setAndSaveApplicationStyle(ApplicationStyle.getApplicationStyle());
        }
        return applicationStyleJson.getSetting().get();
    }

    /**
     * get piece style saved in the configuration files.
     * 
     * @return piece style saved in the configuration files
     * @throws IOException
     */
    public static PieceStyleEnum getSavedPieceStyle() throws IOException {
        if (pieceStyleJson.getSetting().isEmpty()) {
            setAndSavePieceStyle(PieceStyle.getPieceStyle());
        }
        return pieceStyleJson.getSetting().get();
    }

    /**
     * get piece style saved in the configuration files.
     * 
     * @return piece style saved in the configuration files
     * @throws IOException
     */
    public static double getSavedSoundVolume() throws IOException {
        if (soundVolumeJson.getSetting().isEmpty()) {
            setAndSaveSoundVolume(Sound.getVolume());
        }
        return soundVolumeJson.getSetting().get();
    }

}
