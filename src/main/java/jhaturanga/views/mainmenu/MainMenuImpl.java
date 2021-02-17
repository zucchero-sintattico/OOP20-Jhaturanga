package jhaturanga.views.mainmenu;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;

public final class MainMenuImpl implements MainMenu {

    private Stage stage;

    private LoginController controller;

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
