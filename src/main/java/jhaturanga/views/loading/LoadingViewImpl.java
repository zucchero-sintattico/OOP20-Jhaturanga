package jhaturanga.views.loading;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.StageStyle;
import jhaturanga.controllers.loading.LoadingController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LoadingViewImpl extends AbstractView implements LoadingView {

    /**
     * Loading time.
     */
    private static final int LOADING_TIME = 1500;

    private volatile boolean loaded;

    @FXML
    private ProgressBar progress;

    @Override
    public void init() {
        this.getStage().resizableProperty().set(false);
        this.getStage().initStyle(StageStyle.UNDECORATED);
        new Thread(this::load).start();
    }

    private void runLoadingBar() {
        final int percentage = 100;
        final double threshold = 0.70;
        for (double i = 1; i <= percentage; i++) {

            final double percentageValue = i / 100;
            while (percentageValue > threshold && !this.loaded) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater(() -> this.progress.setProgress(percentageValue));
            try {
                Thread.sleep(LOADING_TIME / percentage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> {
            this.getStage().close();
            PageLoader.newPage(Pages.SPLASH, this.getController().getModel());
        });
    }

    private void load() {
        this.getLoadingController().load();
        this.loaded = true;
    }

    @FXML
    public void initialize() {
        new Thread(this::runLoadingBar).start();
    }

    @Override
    public LoadingController getLoadingController() {
        return (LoadingController) this.getController();
    }

}
