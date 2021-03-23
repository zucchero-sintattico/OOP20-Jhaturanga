package jhaturanga;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.instance.ApplicationInstance;
import jhaturanga.instance.ApplicationInstanceImpl;
import jhaturanga.views.oldlogin.CommandLineLoginView;

public final class Launcher {

    private static final String COMMAND_LINE_PARAMETER = "-cmd";

    private Launcher() {

    }

    private static void startCommandLine() {
        // Create the instance of the application data
        final ApplicationInstance instance = new ApplicationInstanceImpl();

        final LoginController loginController = new LoginControllerImpl();
        loginController.setApplicationInstance(instance);

        final CommandLineLoginView view = new CommandLineLoginView();
        view.setController(loginController);

        loginController.setView(view);

        view.run();
    }

    private static void startJavaFx(final String[] args) {
        Jhaturanga.main(args);
    }

    public static void main(final String[] args) {

        if (args.length > 0) {
            if (COMMAND_LINE_PARAMETER.equals(args[0])) {
                startCommandLine();
            }
        } else {
            startJavaFx(args);
        }

    }

}
