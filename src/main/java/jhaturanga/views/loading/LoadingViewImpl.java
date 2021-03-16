package jhaturanga.views.loading;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.StageStyle;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LoadingViewImpl extends AbstractView implements LoadingView {

    @FXML
    private ProgressBar progress;

    private void load() {
        final int milliseconds = 3000;
        final int percentage = 100;

        this.progress.setStyle("-fx-accent: #fff");
        for (double i = 1; i <= percentage; i++) {
            final double percentageValue = i / 100;
            Platform.runLater(() -> this.progress.setProgress(percentageValue));
            try {
                Thread.sleep(milliseconds / percentage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> {
            try {
                this.getStage().close();
                PageLoader.newPage(Pages.SPLASH, this.getController().getModel());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void init() {
        this.getStage().resizableProperty().set(false);
        this.getStage().initStyle(StageStyle.UNDECORATED);
    }

    @FXML
    public void initialize() {
        new Thread(this::load).start();
    }

}
