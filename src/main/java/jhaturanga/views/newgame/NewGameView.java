package jhaturanga.views.newgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View where the user choose if play online of offline.
 */
public final class NewGameView extends AbstractJavaFXView {

    @Override
    public void init() {
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    @FXML
    public void onOfflineClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SELECT_GAME, this.getController().getApplicationInstance());
    }

    @FXML
    public void onOnlineClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.ONLINE, this.getController().getApplicationInstance());
    }
}
