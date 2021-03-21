package jhaturanga.views.oldlogin;

import java.util.Optional;

import javafx.event.Event;
import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.home.HomeControllerImpl;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.model.user.User;
import jhaturanga.views.AbstractView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.game.CommandLineGameView;

public final class CommandLineLoginView extends AbstractView implements OldLoginView, CommandLineView {

    private static final String BANNER = "     ____.__            __                                            \n"
            + "    |    |  |__ _____ _/  |_ __ ______________    ____    _________   \n"
            + "    |    |  |  \\\\__  \\\\   __\\  |  \\_  __ \\__  \\  /    \\  / ___\\__  \\  \n"
            + "/\\__|    |   Y  \\/ __ \\|  | |  |  /|  | \\// __ \\|   |  \\/ /_/  > __ \\_\n"
            + "\\________|___|  (____  /__| |____/ |__|  (____  /___|  /\\___  (____  /\n"
            + "              \\/     \\/                       \\/     \\//_____/     \\/ ";

    private final CommandLine console = new CommandLine();
    private String username;
    private String password;
    private User user;

    @Override
    public LoginController getLoginController() {
        return (LoginController) this.getController();
    }

    @Override
    public void login(final Event event) {
        this.console.clearConsole();

        this.console.println("Login:");
        this.username = this.console.readLine("\tUsername : ");
        this.password = this.console.readPassword("\tPassword : ");

        final Optional<User> tempUser = this.getLoginController().login(this.username, this.password);
        if (tempUser.isPresent()) {
            this.user = tempUser.get();
            this.console.println("Login Succeded - Welcome " + this.user.getUsername());
        } else {
            this.console.println("Username or Password is wrong!!");
        }
    }

    @Override
    public void register(final Event event) {
        this.console.clearConsole();

        this.console.println("Register:");
        this.username = this.console.readLine("\tUsername : ");
        this.password = this.console.readPassword("\tPassword : ");

        final Optional<User> tempUser = this.getLoginController().register(this.username, this.password);
        if (tempUser.isPresent()) {
            this.user = tempUser.get();
            this.console.println("\nRegister Succeded - Welcome " + this.user.getUsername());
        } else {
            this.console.println("\nSomething went wrong during register process");
        }
    }

    public void run() {
        this.console.println(BANNER + "\n\n\n");

        this.console.println("It's login time: ");
        while (this.user == null) {

            final String choice = this.console.readLine("Please select if you want to login(0) or register(1) : ");
            switch (choice) {
            case "0":
                this.login(null);
                break;
            case "1":
                this.register(null);
                break;
            default:
                break;
            }

        }

        this.console.readLine("Press enter to continue to the home page...");
        this.goToHomePage();
    }

    private void goToHomePage() {

        new Thread(() -> {

            final CommandLineGameView view = new CommandLineGameView();
            final HomeControllerImpl controller = new HomeControllerImpl();
            controller.setModel(this.getLoginController().getModel());
            view.setController(controller);
            view.run();

        }).start();

    }

    @Override
    public void init() {

    }

}
