package jhaturanga.controllers.settings;

import java.io.IOException;

import jhaturanga.commons.settings.SettingManager;
import jhaturanga.commons.settings.media.style.application.ApplicationStyle;
import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;
import jhaturanga.commons.settings.media.style.piece.PieceStyle;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.controllers.AbstractController;


public final class SettingsControllerImpl extends AbstractController implements SettingsController {

    @Override
    public void setApplicationStyle(final ApplicationStyleEnum style) {
        try {
            SettingManager.setAndSaveApplicationStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ApplicationStyleEnum getCurrentApplicationStyle() {
        try {
            return SettingManager.getSavedApplicatioStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApplicationStyle.getApplicationStyle();
    }

    @Override
    public void setPlayerStyle(final PieceStyleEnum style) {
        try {
            SettingManager.setAndSavePieceStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PieceStyleEnum getCurrentPlayerStyle() {
        try {
            return SettingManager.getSavedPieceStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PieceStyle.getPieceStyle();
    }

}
