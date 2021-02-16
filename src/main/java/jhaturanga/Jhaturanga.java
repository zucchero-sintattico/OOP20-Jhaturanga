package jhaturanga;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jhaturanga.views.splash.SplashView;

public final class Jhaturanga extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("pages/splash.fxml"));

        final Parent root = loader.load();
        final SplashView controller = loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Jhaturanga");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
