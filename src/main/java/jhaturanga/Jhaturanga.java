package jhaturanga;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import jhaturanga.instance.ApplicationInstance;
import jhaturanga.instance.ApplicationInstanceImpl;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class Jhaturanga extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        // The istance of the application data for this session
        final ApplicationInstance instance = new ApplicationInstanceImpl();

        PageLoader.switchPage(primaryStage, Pages.LOADING, instance);

    }

}
