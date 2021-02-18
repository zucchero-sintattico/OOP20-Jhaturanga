package jhaturanga.controllers.settings;

import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.controllers.AbstractController;

public final class SettingsControllerImpl extends AbstractController implements SettingsController {

    @Override
    public void setApplicationStyle(final ApplicationStyleEnum style) {
        ApplicationStyle.setApplicationStyle(style);
    }

    @Override
    public ApplicationStyleEnum getCurrentApplicationStyle() {
        // TODO Auto-generated method stub
        return null;
    }

}
