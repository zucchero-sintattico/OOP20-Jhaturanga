package jhaturanga.views.settings;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import jhaturanga.commons.style.ApplicationStyle.ApplicationsStyleEnum;
import jhaturanga.commons.style.StyleBilderImpl;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;

public final class SettingViewsImpl implements SettingsView {

    @FXML
    private ChoiceBox<ApplicationsStyleEnum> styleListChoiceBox;
    private Controller controller;
    private Stage stage;

    @FXML
    void initialize() {
        styleListChoiceBox.getItems().addAll(ApplicationsStyleEnum.values());
    }

    @FXML
    void saveButton(final Event event) {
        StyleBilderImpl.setDark(getStage());
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

}
