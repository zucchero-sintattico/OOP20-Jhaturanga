package jhaturanga.views.splash;

import javafx.event.Event;
import javafx.fxml.FXML;
import jhaturanga.Launcher;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic implementation for the Splash View.
 */
public final class SplashView extends AbstractJavaFXView {

    private void startCommandLine() {
        Launcher.main(new String[] { "-cmd" });
    }

    @FXML
    public void onCommandLineClick(final Event event) {
        this.getStage().close();
        new Thread(this::startCommandLine).start();
    }

    @FXML
    public void onJavaFxClick(final Event event) {
        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getApplicationInstance());
    }

    @Override
    public void init() {
    }

}
