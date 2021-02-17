package jhaturanga.pages;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.settings.SettingsController;

public final class SettingsPage implements Page {

    @Override
    public String getName() {
        return "Settings";
    }

    @Override
    public Class<? extends Controller<? extends Page>> getControllerClass() {
        return SettingsController.class;
    }

}
