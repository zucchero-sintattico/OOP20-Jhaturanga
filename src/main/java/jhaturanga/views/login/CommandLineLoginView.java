package jhaturanga.views.login;

import jhaturanga.commons.console.CommandLineConsole;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.views.BasicView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.setup.CommandLineSetupView;

/**
 * The command line version of the Login View.
 */
public final class CommandLineLoginView extends BasicView implements CommandLineView {

    private static final String BANNER = "     ____.__            __                                            \n"
            + "    |    |  |__ _____ _/  |_ __ ______________    ____    _________   \n"
            + "    |    |  |  \\\\__  \\\\   __\\  |  \\_  __ \\__  \\  /    \\  / ___\\__  \\  \n"
            + "/\\__|    |   Y  \\/ __ \\|  | |  |  /|  | \\// __ \\|   |  \\/ /_/  > __ \\_\n"
            + "\\________|___|  (____  /__| |____/ |__|  (____  /___|  /\\___  (____  /\n"
            + "              \\/     \\/                       \\/     \\//_____/     \\/ ";

    private final CommandLineConsole console = new CommandLineConsole();
    private String username;
    private String password;
    private boolean logged;

    public void login() {
        this.console.clearConsole();

        this.console.println("Login:");
        this.username = this.console.readLine("\tUsername : ");
        this.password = this.console.readPassword("\tPassword : ");

        this.logged = this.getLoginController().login(this.username, this.password);
        if (this.logged) {
            this.console.println("Login Succeded");
        } else {
            this.console.println("Username or Password is wrong!!");
        }
    }

    public void register() {
        this.console.clearConsole();

        this.console.println("Register:");
        this.username = this.console.readLine("\tUsername : ");
        this.password = this.console.readPassword("\tPassword : ");

        this.logged = this.getLoginController().register(this.username, this.password);
        if (this.logged) {
            this.console.println("\nRegister Succeded");
        } else {
            this.console.println("\nSomething went wrong during register process");
        }
    }

    public void run() {
        this.console.println(BANNER + "\n\n\n");

        this.console.println("It's login time: ");
        while (!this.logged) {

            final String choice = this.console.readLine("Please select if you want to login(0) or register(1) : ");
            switch (choice) {
            case "0":
                this.login();
                break;
            case "1":
                this.register();
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
            final CommandLineSetupView view = new CommandLineSetupView();
            final SetupController controller = new SetupControllerImpl();
            controller.setModel(this.getController().getModel());
            view.setController(controller);
            view.run();
        }).start();

    }

    private LoginController getLoginController() {
        return (LoginController) this.getController();
    }

}
