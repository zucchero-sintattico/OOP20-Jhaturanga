package jhaturanga.views.splash;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jhaturanga.Launcher;
import jhaturanga.controllers.Controller;
import jhaturanga.views.PageLoader;

public class SplashViewImpl implements SplashView {

    private static final double HOVER_IMAGE_SCALE = 2;

    private Stage stage;

    @FXML
    private Pane cliPane;

    @FXML
    private Pane javaFxPane;

    @FXML
    private ImageView cliLogoImage;

    @FXML
    private ImageView javaFxLogoImage;

    @Override
    public final Controller getController() {
        return null;
    }

    @Override
    public void setController(final Controller controller) {

    }

    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @FXML
    private void cliPaneHoverEnter() {
        this.cliLogoImage.fitWidthProperty().set(this.cliLogoImage.getFitWidth() * HOVER_IMAGE_SCALE);
    }

    @FXML
    private void cliPaneHoverExit() {
        this.cliLogoImage.fitWidthProperty().set(this.cliLogoImage.getFitWidth() / HOVER_IMAGE_SCALE);
    }

    @FXML
    private void cliPaneClick() {
        this.getStage().close();
        new Thread(() -> {

            try {
                final String[] args = new String[1];
                args[0] = "-cmd";
                Launcher.main(args);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @FXML
    private void javaFxPaneHoverEnter() {
        this.javaFxLogoImage.fitWidthProperty().set(this.javaFxLogoImage.getFitWidth() * HOVER_IMAGE_SCALE);
    }

    @FXML
    private void javaFxPaneHoverExit() {
        this.javaFxLogoImage.fitWidthProperty().set(this.javaFxLogoImage.getFitWidth() / HOVER_IMAGE_SCALE);
    }

    @FXML
    private void javaFxPaneClick() throws IOException {
        PageLoader.switchPage(this.getStage(), "login");
    }

}
