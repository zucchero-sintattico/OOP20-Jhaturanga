package jhaturanga.views.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.event.Event;
import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;

public final class CommandLineLoginView implements LoginView {

    private final String BANNER = "     ____.__            __                                            \n"
            + "    |    |  |__ _____ _/  |_ __ ______________    ____    _________   \n"
            + "    |    |  |  \\\\__  \\\\   __\\  |  \\_  __ \\__  \\  /    \\  / ___\\__  \\  \n"
            + "/\\__|    |   Y  \\/ __ \\|  | |  |  /|  | \\// __ \\|   |  \\/ /_/  > __ \\_\n"
            + "\\________|___|  (____  /__| |____/ |__|  (____  /___|  /\\___  (____  /\n"
            + "              \\/     \\/                       \\/     \\//_____/     \\/ ";
    private LoginController controller;
    private BufferedReader console;
    private String username;
    private String password;

    public CommandLineLoginView() {
        this.console = new BufferedReader(new InputStreamReader(System.in));
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
    public Stage getStage() {
        return null;
    }

    @Override
    public void login(final Event event) {
    }

    @Override
    public void register(final Event event) {
    }

    private String readLine() {
        try {
            return this.console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readLine(final String message) {
        System.out.print(message);
        return this.readLine();
    }

    public void run() {
        System.out.println(BANNER + "\n\n\n");

        System.out.println("Login: ");
        this.username = this.readLine("Username : ");
        this.password = this.readLine("Password : ");
        System.out.println("Username = " + this.username + " - Password = " + this.password);

    }

}
