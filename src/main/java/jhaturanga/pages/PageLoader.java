package jhaturanga.pages;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.controllers.Controller;
import jhaturanga.views.View;

public final class PageLoader {

    private static final String PATH_START = "pages/";
    private static final String PATH_END = ".fxml";

    private PageLoader() {
    }

    /**
     * @param <P>        - the page type of the actual page
     * @param <T>        - the page type of the new page
     * 
     * @param stage      - the stage to switch content
     * @param page       - the page to load
     * @param controller - the controller of the page
     * @throws IOException if file not found
     */
    public static <P extends Page, T extends Page> void switchPage(final Stage stage, final T page,
            final Controller<P> controller) throws IOException {

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page.getName() + PATH_END));

        final Parent root = loader.load();

        final View<T> view = loader.getController();
        view.getController().setModel(controller.getModel());
        view.setController(view.getNewControllerInstance());

        view.setStage(stage);
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
        stage.show();
    }

    /**
     * @param <P>        - the page type of the actual page
     * @param <T>        - the page type of the new page
     * @param page       - the page to load
     * @param controller - the controller of the page
     * @throws IOException if file not found
     */
    public static <P extends Page, T extends Page> void newPage(final P page, final Controller<P> controller)
            throws IOException {
        final Stage stage = new Stage();
        switchPage(stage, page, controller);
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
