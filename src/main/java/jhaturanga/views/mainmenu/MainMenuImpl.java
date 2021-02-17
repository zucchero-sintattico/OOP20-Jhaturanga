package jhaturanga.views.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public final class MainMenuImpl implements MainMenu {

    private Stage stage;
    private LoginController controller;

    @FXML
    private ChoiceBox<GameTypesEnum> gameModeChoices;

    @FXML
    void initialize() {
        gameModeChoices.getItems().addAll(GameTypesEnum.values());
        gameModeChoices.setValue(GameTypesEnum.CLASSIC_GAME);
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
