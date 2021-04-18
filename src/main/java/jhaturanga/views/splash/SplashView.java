package jhaturanga.views.splash;

import javafx.event.Event;
import javafx.fxml.FXML;
import jhaturanga.Launcher;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View where the user choose the platform where to play (JavaFX or Command
 * Line).
 */
public final class SplashView extends AbstractJavaFXView {

    private void startCommandLine() {
        Launcher.main(new String[] { "-cmd" });
    }

    /**
     * Handle the click of the user on the Command Line button.
     * 
     * @param event
     */
    @FXML
    public void onCommandLineClick(final Event event) {
        this.getStage().close();
        new Thread(this::startCommandLine).start();
    }

    /**
     * Handle the click of the user on the JavaFx button.
     * 
     * @param event
     */
    @FXML
    public void onJavaFxClick(final Event event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.LOGIN,
                this.getController().getModel());
    }

    @Override
    public void init() {
    }

}
