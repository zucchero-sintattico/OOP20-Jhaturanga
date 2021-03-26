package jhaturanga.views.settings;

import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.views.JavaFXView;

/**
 * The view of the settings page.
 */
public interface SettingsView extends JavaFXView {

    /**
     * Get the settings controller.
     * 
     * @return the settings controller
     */
    SettingsController getSettingsController();
}
