package jhaturanga;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import jhaturanga.views.PageLoader;

public final class Jhaturanga extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        PageLoader.switchPage(primaryStage, "splash");

    }

}
