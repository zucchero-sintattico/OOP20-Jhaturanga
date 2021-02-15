package jhaturanga.views.settings;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyle.ApplicationStyleEnum;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.views.PageLoader;

public final class SettingViewsImpl implements SettingsView {

    @FXML
    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;
    private Controller controller;
    private Stage stage;

    @FXML
    void initialize() {
        styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
    }

    @FXML
    void saveButton(final Event event) {
        ApplicationStyle.setApplicationStyle(styleListChoiceBox.getValue());
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setController(final Controller controller) {
        this.controller = (LoginController) controller;
    }

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;

    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @FXML
    public void backToLogin(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), "login");

    }

}
