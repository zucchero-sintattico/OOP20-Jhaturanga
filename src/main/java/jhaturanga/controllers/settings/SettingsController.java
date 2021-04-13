package jhaturanga.controllers.settings;

import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.controllers.Controller;

/**
 * The controller for the settings page view.
 */
public interface SettingsController extends Controller {

    /**
     * Set the style of application.
     * 
     * @param style - the application style to be setted
     */
    void setApplicationStyle(ApplicationStyleConfigurationObjectStrategy style);

    /**
     * Get the current application style.
     * 
     * @return the current application style.
     */
    ApplicationStyleConfigurationObjectStrategy getCurrentApplicationStyle();

    /**
     * Set the piece style.
     * 
     * @param style - the piece style to be setted.
     */
    void setPieceStyle(PieceStyleEnum style);

    /**
     * Get the current piece style.
     * 
     * @return the current piece style.
     */
    PieceStyleEnum getCurrentPieceStyle();

    /**
     * Set the application volume.
     * 
     * @param volume - the volume to be setted
     */
    void setApplicationVolume(double volume);

    /**
     * Get the current application volume.
     * 
     * @return the current application volume.
     */
    double getCurrentApplicationVolume();

}
