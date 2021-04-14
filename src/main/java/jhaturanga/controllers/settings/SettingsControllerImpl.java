package jhaturanga.controllers.settings;

import java.io.IOException;

import jhaturanga.commons.settings.SettingMediator;
import jhaturanga.commons.settings.dynamicconfiguration.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.dynamicconfiguration.PieceStyleconfigurationObjectStrategy;
import jhaturanga.commons.settings.media.sound.Sound;
import jhaturanga.commons.settings.media.style.ApplicationStyle;
import jhaturanga.commons.settings.media.style.PieceStyle;
import jhaturanga.controllers.BasicController;

/**
 * Basic implementation for the SettingsController.
 */
public final class SettingsControllerImpl extends BasicController implements SettingsController {

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationStyle(final ApplicationStyleConfigurationObjectStrategy style) {
        try {
            SettingMediator.setAndSaveApplicationStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationStyleConfigurationObjectStrategy getCurrentApplicationStyle() {
        try {
            return SettingMediator.getSavedApplicatioStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApplicationStyle.getApplicationStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPieceStyle(final PieceStyleconfigurationObjectStrategy style) {
        try {
            SettingMediator.setAndSavePieceStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PieceStyleconfigurationObjectStrategy getCurrentPieceStyle() {
        try {
            return SettingMediator.getSavedPieceStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PieceStyle.getPieceStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationVolume(final double volume) {
        try {
            SettingMediator.setAndSaveSoundVolume(volume);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCurrentApplicationVolume() {
        try {
            return SettingMediator.getSavedSoundVolume();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Sound.getVolume();
    }

}
