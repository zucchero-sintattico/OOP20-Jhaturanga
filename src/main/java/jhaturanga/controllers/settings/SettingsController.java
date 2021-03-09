package jhaturanga.controllers.settings;

import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.controllers.Controller;

/**
 * The controller for the settings page.
 */
public interface SettingsController extends Controller {

    // TODO: TOMMASO DOCUMENTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    void setApplicationStyle(ApplicationStyleEnum style);

    ApplicationStyleEnum getCurrentApplicationStyle();

}
