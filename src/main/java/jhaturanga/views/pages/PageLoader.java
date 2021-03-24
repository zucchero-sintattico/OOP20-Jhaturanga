package jhaturanga.views.pages;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.controllers.Controller;
import jhaturanga.instance.ApplicationInstance;
import jhaturanga.views.JavaFXView;

public final class PageLoader {

    private static final int ANIMATION_DURATION = 350;
    private static final String PATH_START = "pages/";
    private static final String PATH_END = ".fxml";

    private PageLoader() {
    }

    private static void loadStyle(final Stage stage) {
        stage.setMinHeight(0.0);
        stage.setMinWidth(0.0);
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
    }

    /**
     * 
     * @param stage               - the stage to switch content
     * @param page                - the page to load
     * @param applicationInstance - the application instance
     * @throws IOException if file not found
     */
    public static void switchPage(final Stage stage, final Pages page, final ApplicationInstance applicationInstance) {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            // System.exit(0);
        });

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));

        loadStyle(stage);
        final JavaFXView view = loader.getController();

        final Controller controller = page.getNewControllerInstance();
        controller.setApplicationInstance(applicationInstance);
        controller.setView(view);

        view.setController(controller);
        view.setStage(stage);
        view.init();

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION), root);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * 
     * @param stage      - the stage to switch content
     * @param page       - the page to load
     * @param controller - the controller
     * @throws IOException if file not found
     */
    public static void switchPageWithSameController(final Stage stage, final Pages page, final Controller controller) {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            // System.exit(0);
        });

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(root));
        loadStyle(stage);

        final JavaFXView view = loader.getController();
        controller.setView(view);

        view.setController(controller);
        view.setStage(stage);
        view.init();

        stage.show();
    }

    /**
     * 
     * @param page                - the page to load
     * @param applicationInstance - the application instance
     * @throws IOException if file not found
     */
    public static void newPage(final Pages page, final ApplicationInstance applicationInstance) {
        final Stage stage = new Stage();
        switchPage(stage, page, applicationInstance);
    }

    /**
     * 
     * @param page       - the page to load
     * @param controller - the controller
     * @throws IOException if file not found
     */
    public static void newPageWithSameController(final Pages page, final Controller controller) {
        final Stage stage = new Stage();
        switchPageWithSameController(stage, page, controller);
    }

    /**
     * @param stage - the stage to load
     */
    public static void updatePage(final Stage stage) {
        loadStyle(stage);
    }

}