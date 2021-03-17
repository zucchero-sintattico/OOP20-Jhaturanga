package jhaturanga;

import java.io.IOException;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.model.Model;
import jhaturanga.model.ModelImpl;
import jhaturanga.views.login.CommandLineLoginView;

public final class Launcher {

    private static final String COMMAND_LINE_PARAMETER = "-cmd";

    private Launcher() {

    }

    private static void startCommandLine() throws IOException {
        // Create the first instance of the model for this session
        final Model model = new ModelImpl();

        final LoginController loginController = new LoginControllerImpl();
        loginController.setModel(model);

        final CommandLineLoginView view = new CommandLineLoginView();
        view.setController(loginController);

        loginController.setView(view);

        view.run();
    }

    private static void startJavaFx(final String[] args) {
        Jhaturanga.main(args);
    }

    public static void main(final String[] args) throws IOException {

        if (args.length > 0) {
            if (COMMAND_LINE_PARAMETER.equals(args[0])) {
                startCommandLine();
            }
        } else {
            startJavaFx(args);
        }

    }

}
