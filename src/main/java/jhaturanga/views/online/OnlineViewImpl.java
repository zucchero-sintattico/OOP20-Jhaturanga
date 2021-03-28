package jhaturanga.views.online;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.View;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

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
