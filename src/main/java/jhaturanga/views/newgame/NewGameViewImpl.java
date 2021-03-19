package jhaturanga.views.newgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class NewGameViewImpl extends AbstractView implements NewGameView {

    @Override
    public void init() {
//        this.getStage().setMinHeight(this.getStage().getHeight());
//        this.getStage().setMinWidth(this.getStage().getWidth());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @FXML
    public void onOfflineClick(final ActionEvent event) {
        System.out.println("OFFLINE");
    }

    @FXML
    public void onOnlineClick(final ActionEvent event) {
        System.out.println("ONLINE");
    }
}
