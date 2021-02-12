package jhaturanga;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.views.login.CommandLineLoginView;

public final class Launcher {

    private Launcher() {

    }

    public static void main(final String[] args) {

        final CommandLineLoginView view = new CommandLineLoginView();
        final LoginController loginController = null; // new LoginControllerImpl(view);
        view.setController(loginController);
        view.run();

    }

}
