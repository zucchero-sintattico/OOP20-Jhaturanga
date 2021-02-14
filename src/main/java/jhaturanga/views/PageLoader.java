package jhaturanga.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class PageLoader {

    private static final String PATH_START = "res/pages/";
    private static final String PATH_END = ".fxml";

    public PageLoader() {
    }

    public static void switchPage(final Stage stage, final String page) throws IOException {

        final URL url = new File(PATH_START + page + PATH_END).toURI().toURL();
        final Parent root = FXMLLoader.load(url);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void newPage(final String page) throws IOException {
        final Stage stage = new Stage();
        switchPage(stage, page);
    }

}
