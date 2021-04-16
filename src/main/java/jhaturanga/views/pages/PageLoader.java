package jhaturanga.views.pages;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jhaturanga.commons.settings.SettingMediator;
import jhaturanga.controllers.Controller;
import jhaturanga.model.Model;
import jhaturanga.views.JavaFXView;

/**
 * The loader of the pages. It manages the loading of the first page and the
 * switch of the page during the session.
 */
public final class PageLoader {

    private static final int ANIMATION_DURATION = 350;
    private static final String PATH_START = "pages/";
    private static final String PATH_END = ".fxml";

    private PageLoader() {
    }

    private static void loadStyle(final Stage stage) {
        stage.getScene().getStylesheets().clear();
        try {

            stage.getScene().getStylesheets()
                    .add(SettingMediator.getSavedApplicatioStyle().getFilePath().toUri().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch a page.
     * 
     * @param stage               - the stage to switch content
     * @param page                - the page to load
     * @param applicationInstance - the application instance
     */
    public static void switchPage(final Stage stage, final Pages page, final Model applicationInstance) {
        final Controller controller = page.getNewControllerInstance();
        controller.setApplicationInstance(applicationInstance);
        switchPageWithSpecifiedController(stage, page, controller);
    }

    /**
     * Switch a page passing a specified controller.
     * 
     * @param stage      - the stage to switch content
     * @param page       - the page to load
     * @param controller - the controller
     */
    public static void switchPageWithSpecifiedController(final Stage stage, final Pages page,
            final Controller controller) {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }

        loadStyle(stage);

        stage.setMinHeight(((AnchorPane) stage.getScene().getRoot()).getMinHeight());
        stage.setMinWidth(((AnchorPane) stage.getScene().getRoot()).getMinWidth());

        if (root != null) {

            root.scaleXProperty().bind(Bindings.min(stage.widthProperty().divide(stage.minWidthProperty()),
                    stage.heightProperty().divide(stage.minHeightProperty())));

            root.scaleYProperty().bind(root.scaleXProperty());
        }

        final JavaFXView view = loader.getController();
        controller.setView(view);

        view.setController(controller);
        view.setStage(stage);
        view.init();

        final FadeTransition fadeIn = new FadeTransition(Duration.millis(ANIMATION_DURATION), root);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        stage.show();
    }

    /**
     * Create a new page.
     * 
     * @param page                - the page to load
     * @param applicationInstance - the application instance
     */
    public static void newPage(final Pages page, final Model applicationInstance) {
        final Stage stage = new Stage();
        switchPage(stage, page, applicationInstance);
    }

    /**
     * Create a new page with the specified controller.
     * 
     * @param page       - the page to load
     * @param controller - the controller
     * @throws IOException if file not found
     */
    public static void newPageWithSpecifiedController(final Pages page, final Controller controller) {
        final Stage stage = new Stage();
        switchPageWithSpecifiedController(stage, page, controller);
    }

}
