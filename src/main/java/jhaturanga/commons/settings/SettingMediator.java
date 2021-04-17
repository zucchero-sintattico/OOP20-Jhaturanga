package jhaturanga.commons.settings;

import java.io.IOException;

import jhaturanga.commons.settings.config.ApplicationStyleConfigStrategy;
import jhaturanga.commons.settings.config.PieceStyleConfigStrategy;
import jhaturanga.commons.settings.media.sound.Sound;
import jhaturanga.commons.settings.media.style.ApplicationStyle;
import jhaturanga.commons.settings.media.style.PieceStyle;
import jhaturanga.commons.settings.storage.ApplicationStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.PiecesStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SettingsDataStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SoundDateStorageStrategy;

/**
 * The Class SettingMediator.
 */
public final class SettingMediator {

    /** The application style by json file. */
    private static SettingsDataStorageJsonStrategy<ApplicationStyleConfigStrategy> applicationStyleJson = new ApplicationStyleDateStorageJsonStrategy();

    /** The piece style by json file. */
    private static SettingsDataStorageJsonStrategy<PieceStyleConfigStrategy> pieceStyleJson = new PiecesStyleDateStorageJsonStrategy();

    /** The sound volume by json file. */
    private static SettingsDataStorageJsonStrategy<Double> soundVolumeJson = new SoundDateStorageStrategy();

    /**
     * this class is used to communicate the configuration files with the classes
     * that manage the style of the application.
     */
    private SettingMediator() {
    }

    /**
     * Sets the and save application style.
     *
     * @param style the new and save application style
     * @throws IOException Signals that an I/O exception has occurred.
     * 
     *                     saves the application style from the configuration file
     *                     and temporarily sets it in the application style
     *                     management class
     */
    public static void setAndSaveApplicationStyle(final ApplicationStyleConfigStrategy style) throws IOException {
        ApplicationStyle.setApplicationStyle(style);
        applicationStyleJson.setSetting(style);
    }

    /**
     * Sets the and save piece style.
     *
     * @param style the new and save piece style
     * @throws IOException Signals that an I/O exception has occurred.
     */
    /*
     * saves the piece style from the configuration file and temporarily sets it in
     * the piece style management class.
     */
    public static void setAndSavePieceStyle(final PieceStyleConfigStrategy style) throws IOException {
        PieceStyle.setPieceStyle(style);
        pieceStyleJson.setSetting(style);
    }

    /**
     * Sets the and save sound volume.
     *
     * @param volume the new and save sound volume
     * @throws IOException Signals that an I/O exception has occurred.
     */
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
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static ApplicationStyleConfigStrategy getSavedApplicatioStyle() throws IOException {
        if (applicationStyleJson.getSetting().isEmpty()) {
            setAndSaveApplicationStyle(ApplicationStyle.getApplicationStyle());
        }
        return applicationStyleJson.getSetting().get();
    }

    /**
     * get piece style saved in the configuration files.
     *
     * @return piece style saved in the configuration files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static PieceStyleConfigStrategy getSavedPieceStyle() throws IOException {
        if (pieceStyleJson.getSetting().isEmpty()) {
            setAndSavePieceStyle(PieceStyle.getPieceStyle());
        }
        return pieceStyleJson.getSetting().get();
    }

    /**
     * get piece style saved in the configuration files.
     *
     * @return piece style saved in the configuration files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static double getSavedSoundVolume() throws IOException {
        if (soundVolumeJson.getSetting().isEmpty()) {
            setAndSaveSoundVolume(Sound.getVolume());
        }
        return soundVolumeJson.getSetting().get();
    }

}
