package jhaturanga.views.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class SettingsViewImpl extends AbstractJavaFXView {

    @Override
    public void init() {
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    private SettingsController getSettingsController() {
        return (SettingsController) this.getController();
    }

}
