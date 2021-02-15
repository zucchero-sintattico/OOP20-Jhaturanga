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
        this.controller = (HomeController) controller;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    public void run() {

    }

    @Override
    public void setStage(Stage stage) {
        // TODO Auto-generated method stub

    }

}
