package jhaturanga.pages;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.controllers.Controller;
import jhaturanga.model.Model;
import jhaturanga.views.View;

public final class PageLoader {

    private static final String PATH_START = "pages/";
    private static final String PATH_END = ".fxml";

    private PageLoader() {
    }

    /**
     * 
     * @param stage - the stage to switch content
     * @param page  - the page to load
     * @param model - the model of the application
     * @throws IOException if file not found
     */
    public static void switchPage(final Stage stage, final Pages page, final Model model) throws IOException {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        final Parent root = loader.load();

        final View view = loader.getController();

        final Controller controller = page.getNewControllerInstance();
        controller.setModel(model);

        view.setController(controller);
        view.init();
        view.setStage(stage);
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
        stage.show();
    }

    /**
     * 
     * @param stage      - the stage to switch content
     * @param page       - the page to load
     * @param controller - the controller
     * @throws IOException if file not found
     */
    public static void switchPageWithSameController(final Stage stage, final Pages page, final Controller controller)
            throws IOException {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        final Parent root = loader.load();

        final View view = loader.getController();
        view.setController(controller);
        view.init();
        view.setStage(stage);
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
        stage.show();
    }

    /**
     * 
     * @param page  - the page to load
     * @param model - the model of the application
     * @throws IOException if file not found
     */
    public static void newPage(final Pages page, final Model model) throws IOException {
        final Stage stage = new Stage();
        switchPage(stage, page, model);
    }

    /**
     * 
     * @param page       - the page to load
     * @param controller - the controller
     * @throws IOException if file not found
     */
    public static void newPageWithSameController(final Pages page, final Controller controller) throws IOException {
        final Stage stage = new Stage();
        switchPageWithSameController(stage, page, controller);
    }

    /**
     * 
     * @param stage to load
     */
    public static void updatePage(final Stage stage) {
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
    }

}
