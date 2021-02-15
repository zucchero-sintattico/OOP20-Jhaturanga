package jhaturanga;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Jhaturanga extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        final URL url = new File("res/pages/login.fxml").toURI().toURL();
        final Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Jhaturanga");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
