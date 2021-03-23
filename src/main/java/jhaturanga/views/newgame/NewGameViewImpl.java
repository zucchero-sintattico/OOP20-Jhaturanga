package jhaturanga.views.newgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class NewGameViewImpl extends AbstractView implements NewGameView {

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    @FXML
    public void onOfflineClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SETUP, this.getController().getApplicationInstance());
    }

    @FXML
    public void onOnlineClick(final ActionEvent event) {
        System.out.println("ONLINE");
    }
}
