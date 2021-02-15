package jhaturanga;

import java.io.IOException;

import jhaturanga.views.login.*;

public final class Launcher {
    
    private static final String COMMAND_LINE_PARAMETER = "-cmd";

    private Launcher() {

    }

    private static void startCommandLine() throws IOException {
        //final CommandLineLoginView view = new CommandLineLoginView();
        //final LoginController loginController = new LoginControllerImpl(view);
        //view.setController(loginController);
        //view.run();
    }

    private static void startJavaFx(final String[] args) {
        //Jhaturanga.main(args);
    }

    public static void main(final String[] args) throws IOException {

        if (args.length > 0) {
            if (args[0].equals(COMMAND_LINE_PARAMETER)) {
                startCommandLine();
            }
        } else {
            startJavaFx(args);
        }

    }

}
}
