package jhaturanga.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jhaturanga.commons.style.ApplicationStyle;

public final class PageLoader {

    private static final String PATH_START = "pages/";
    private static final String PATH_END = ".fxml";

    private PageLoader() {
    }

    /**
     * 
     * @param stage to load
     * @param page to load
     * @throws IOException if file not found
     */
    public static void switchPage(final Stage stage, final String page) throws IOException {
        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(PATH_START + page + PATH_END));
        final Parent root = loader.load();
        final View viewController = loader.getController();
        viewController.setStage(stage);
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().clear();    
        stage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
        stage.show();
    }
    
    /**
     * 
     * @param page page to load
     * @throws IOException  if file not found
     */
    public static void newPage(final String page) throws IOException {
        final Stage stage = new Stage();
        switchPage(stage, page);
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
