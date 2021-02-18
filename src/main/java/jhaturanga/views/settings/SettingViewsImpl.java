package jhaturanga.views.settings;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyle.ApplicationStyleEnum;
import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class SettingViewsImpl extends AbstractView implements SettingsView {

    @FXML
    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;

    @Override
    public SettingsController getSettingsController() {
        return (SettingsController) this.getController();
    }

    @FXML
    private void initialize() {
        styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
    }

    @FXML
    private void saveButton(final Event event) {
        ApplicationStyle.setApplicationStyle(styleListChoiceBox.getValue());
        PageLoader.updatePage(getStage());
    }

    @FXML
    private void backToLogin(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
    }

}
