package jhaturanga.views.settings;

import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.views.View;

/**
 * The view of the settings page.
 */
public interface SettingsView extends View {

    /**
     * Get the settings controller.
     * 
     * @return the settings controller
     */
    SettingsController getSettingsController();
}
