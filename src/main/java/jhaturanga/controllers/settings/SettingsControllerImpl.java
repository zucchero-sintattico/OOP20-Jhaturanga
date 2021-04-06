package jhaturanga.controllers.settings;

import java.io.IOException;

import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.commons.style.PieceStyle;
import jhaturanga.commons.style.PieceStyleEnum;
import jhaturanga.commons.style.StyleSettingManager;
import jhaturanga.controllers.BasicController;

public final class SettingsControllerImpl extends BasicController implements SettingsController {

    @Override
    public void setApplicationStyle(final ApplicationStyleEnum style) {
        try {
            StyleSettingManager.setAndSaveApplicationStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ApplicationStyleEnum getCurrentApplicationStyle() {
        try {
            return StyleSettingManager.getSavedApplicatioStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ApplicationStyle.getApplicationStyle();
    }

    @Override
    public void setPlayerStyle(final PieceStyleEnum style) {
        try {
            StyleSettingManager.setAndSavePieceStyle(style);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PieceStyleEnum getCurrentPlayerStyle() {
        try {
            return StyleSettingManager.getSavedPieceStyle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PieceStyle.getPieceStyle();
    }

}
