package jhaturanga.views.splash;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import jhaturanga.Launcher;
import jhaturanga.controllers.splash.SplashController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class SplashViewImpl extends AbstractView implements SplashView {

    private static final double HOVER_IMAGE_SCALE = 2;

    @FXML
    private Pane cliPane;

    @FXML
    private Pane javaFxPane;

    @FXML
    private ImageView cliLogoImage;

    @FXML
    private ImageView javaFxLogoImage;

    @Override
    public SplashController getSplashController() {
        return (SplashController) this.getController();
    }

    @FXML
    public void cliPaneHoverEnter() {
        this.cliLogoImage.fitWidthProperty().set(this.cliLogoImage.getFitWidth() * HOVER_IMAGE_SCALE);
    }

    @FXML
    public void cliPaneHoverExit() {
        this.cliLogoImage.fitWidthProperty().set(this.cliLogoImage.getFitWidth() / HOVER_IMAGE_SCALE);
    }

    @FXML
    public void cliPaneClick() {
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
    public void javaFxPaneHoverEnter() {
        this.javaFxLogoImage.fitWidthProperty().set(this.javaFxLogoImage.getFitWidth() * HOVER_IMAGE_SCALE);
    }

    @FXML
    public void javaFxPaneHoverExit() {
        this.javaFxLogoImage.fitWidthProperty().set(this.javaFxLogoImage.getFitWidth() / HOVER_IMAGE_SCALE);
    }

    @FXML
    public void javaFxPaneClick() throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public void init() {

    }

}
