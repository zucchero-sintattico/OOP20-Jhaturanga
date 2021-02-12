package jhaturanga;

import java.io.IOException;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.views.login.CommandLineLoginView;

public final class Launcher {

    private Launcher() {

    }

    public static void main(final String[] args) throws IOException {

        final CommandLineLoginView view = new CommandLineLoginView();
        final LoginController loginController = new LoginControllerImpl(view);
        view.setController(loginController);
        view.run();

    }

}
