package jhaturanga.controllers.settings;

import jhaturanga.commons.settings.dynamicconfiguration.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.dynamicconfiguration.PieceStyleconfigurationObjectStrategy;
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
    void setPieceStyle(PieceStyleconfigurationObjectStrategy style);

    /**
     * Get the current piece style.
     * 
     * @return the current piece style.
     */
    PieceStyleconfigurationObjectStrategy getCurrentPieceStyle();

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
