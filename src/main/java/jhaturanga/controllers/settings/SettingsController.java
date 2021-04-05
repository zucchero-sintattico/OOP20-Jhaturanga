package jhaturanga.controllers.settings;

import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.controllers.Controller;

/**
 * The controller for the settings page.
 */
public interface SettingsController extends Controller {

    /**
     * set the style of application.
     * 
     * @param style
     */
    void setApplicationStyle(ApplicationStyleEnum style);

    /**
     * get the current application style.
     * 
     * @return the current application style.
     */
    ApplicationStyleEnum getCurrentApplicationStyle();

    /**
     * set the piece style.
     * 
     * @param style
     */
    void setPlayerStyle(PieceStyleEnum style);

    /**
     * get the current piece style.
     * 
     * @return the current piece style.
     */
    PieceStyleEnum getCurrentPlayerStyle();

}
