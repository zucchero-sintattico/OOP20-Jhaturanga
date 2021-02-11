package jhaturanga.views.home;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.home.CommandLineHomeController;
import jhaturanga.controllers.home.HomeController;

public final class CommandLineHomeView implements HomeView {

    private HomeController controller;

    public CommandLineHomeView() {
        this.controller = new CommandLineHomeController();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setController(final Controller controller) {
        // TODO Auto-generated method stub

    }

    @Override
    public Stage getStage() {
        // TODO Auto-generated method stub
        return null;
    }

}
