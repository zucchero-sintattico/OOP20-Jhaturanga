package jhaturanga;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import jhaturanga.model.Model;
import jhaturanga.model.ModelImpl;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;

public final class Jhaturanga extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        // The istance of the model for this session
        final Model model = new ModelImpl();

        PageLoader.switchPage(primaryStage, Pages.LOADING, model);

    }

}
