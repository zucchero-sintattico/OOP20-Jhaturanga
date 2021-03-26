package jhaturanga.controllers.settings;

import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.commons.style.PieceStyle;
import jhaturanga.commons.style.PieceStyleEnum;
import jhaturanga.controllers.AbstractController;

public final class SettingsControllerImpl extends AbstractController implements SettingsController {

    @Override
    public void setApplicationStyle(final ApplicationStyleEnum style) {
        ApplicationStyle.setApplicationStyle(style);
    }

    @Override
    public ApplicationStyleEnum getCurrentApplicationStyle() {
        return ApplicationStyle.getApplicationStyle();
    }

    @Override
    public void setPlayerStyle(final PieceStyleEnum style) {
        PieceStyle.setPieceStyle(style);
    }

    @Override
    public PieceStyleEnum getCurrentPlayerStyle() {
        return PieceStyle.getPieceStyle();
    }

}
