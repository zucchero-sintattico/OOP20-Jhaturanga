package jhaturanga.controllers.settings;

import java.io.IOException;

import jhaturanga.commons.settings.SettingMediator;
import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.media.sound.Sound;
import jhaturanga.commons.settings.media.style.application.ApplicationStyle;
import jhaturanga.commons.settings.media.style.piece.PieceStyle;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
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
    public void setPieceStyle(final PieceStyleEnum style) {
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
    public PieceStyleEnum getCurrentPieceStyle() {
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
