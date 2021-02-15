package jhaturanga.views.home;

import javafx.stage.Stage;
import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.home.CommandLineHomeController;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.views.CommandLineView;

public final class CommandLineHomeView implements HomeView, CommandLineView {

    private HomeController controller;
    private final CommandLine console;

    public CommandLineHomeView() {
        this.controller = new CommandLineHomeController();
        this.console = new CommandLine();
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

    @Override
    public void setStage(final Stage stage) {
    }

    @Override
    public void run() {

        this.console.clearConsole();

        System.out.println("Welcome to Jhaturanga main menu.\nPlease select the which game you want to play : ");
        while (true) {
            final String response = this.console.readLine("Select: ");
            if (response.equals("0")) {

            }
        }

    }

}
