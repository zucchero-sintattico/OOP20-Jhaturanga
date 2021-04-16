package jhaturanga.views.online;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.View;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View of the Online page, where the user choose if he want to create or
 * join a Match.
 */
public final class OnlineViewImpl extends AbstractJavaFXView implements View {

    @Override
    public void init() {

    }

    @FXML
    public void onCreateClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.ONLINE_CREATE, this.getController().getApplicationInstance());
    }

    @FXML
    public void onJoinClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.ONLINE_JOIN, this.getController().getApplicationInstance());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getApplicationInstance());
    }

}
