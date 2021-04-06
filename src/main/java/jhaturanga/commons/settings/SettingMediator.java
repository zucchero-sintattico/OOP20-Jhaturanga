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

    private static SettingsDataStorageJsonStrategy<ApplicationStyleEnum> applicationStyle = new ApplicationStyleDateStorageJsonStrategy();
    private static SettingsDataStorageJsonStrategy<PieceStyleEnum> pieceStyle = new PiecesStyleDateStorageJsonStrategy();
    private static SettingsDataStorageJsonStrategy<Double> soundVolume = new SoundDateStorageStrategy();

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
        applicationStyle.setSetting(style);
    }

    /*
     * saves the piece style from the configuration file and temporarily sets it in
     * the piece style management class.
     */
    public static void setAndSavePieceStyle(final PieceStyleEnum style) throws IOException {
        PieceStyle.setPieceStyle(style);
        pieceStyle.setSetting(style);
    }

    /*
     * saves the volume from the configuration file and temporarily sets it in the
     * sound management class.
     */
    public static void setAndSaveSoundVolume(final double volume) throws IOException {
        Sound.setVolume(volume);
        soundVolume.setSetting(volume);
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
        return applicationStyle.getSetting().get();
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
        return pieceStyle.getSetting().get();
    }

    /**
     * get piece style saved in the configuration files.
     * 
     * @return piece style saved in the configuration files
     * @throws IOException
     */
    public static double getSavedSoundVolume() throws IOException {
        if (soundVolume.getSetting().isEmpty()) {
            setAndSaveSoundVolume(Sound.getVolume());
        }
        return soundVolume.getSetting().get();
    }

}
