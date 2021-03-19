package jhaturanga.views.splash;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import jhaturanga.Launcher;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class SplashViewImpl extends AbstractView implements SplashView {

    private void startCommandLine() {
        try {
            final String[] args = new String[1];
            args[0] = "-cmd";
            Launcher.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onCommandLineClick(final Event event) {
        this.getStage().close();
        new Thread(this::startCommandLine).start();
    }

    @FXML
    public void onJavaFxClick(final Event event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public void init() {

    }

}
