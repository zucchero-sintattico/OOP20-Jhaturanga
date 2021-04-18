package jhaturanga.controllers.settings;

import jhaturanga.commons.settings.config.ApplicationStyleConfigStrategy;
import jhaturanga.commons.settings.config.PieceStyleConfigStrategy;
import jhaturanga.controllers.Controller;

/**
 * The controller for the settings page view.
 */
public interface SettingsController extends Controller {

    /**
     * Set the style of application.
     * 
     * @param style - the application style to be set
     */
    void setApplicationStyle(ApplicationStyleConfigStrategy style);

    /**
     * Get the current application style.
     * 
     * @return the current application style.
     */
    ApplicationStyleConfigStrategy getCurrentApplicationStyle();

    /**
     * Set the piece style.
     * 
     * @param style - the piece style to be set.
     */
    void setPieceStyle(PieceStyleConfigStrategy style);

    /**
     * Get the current piece style.
     * 
     * @return the current piece style.
     */
    PieceStyleConfigStrategy getCurrentPieceStyle();

    /**
     * Set the application volume.
     * 
     * @param volume - the volume to be set
     */
    void setApplicationVolume(double volume);

    /**
     * Get the current application volume.
     * 
     * @return the current application volume.
     */
    double getCurrentApplicationVolume();

}
