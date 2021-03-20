package jhaturanga.views.loading;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.StageStyle;
import jhaturanga.controllers.loading.LoadingController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic Implementation for the Loading View.
 */
public final class LoadingViewImpl extends AbstractView implements LoadingView {

    private static final int LOADING_TIME = 1500;
    private volatile boolean loaded;

    @FXML
    private ProgressBar progress;

    @Override
    public void init() {
        this.getStage().resizableProperty().set(false);
        this.getStage().initStyle(StageStyle.UNDECORATED);
        new Thread(this::load).start();
        new Thread(this::runLoadingBar).start();
    }

    private void runLoadingBar() {
        final double threshold = 0.70;
        for (double i = 1; i <= 100; i++) {
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
                Thread.sleep(LOADING_TIME / 100);
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

}
