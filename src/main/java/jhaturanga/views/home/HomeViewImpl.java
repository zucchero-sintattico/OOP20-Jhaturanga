package jhaturanga.views.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic Implementation of Home Page View.
 */
public final class HomeViewImpl extends AbstractView implements HomeView {

    @FXML
    private Label usernameLabel;

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());

        this.usernameLabel.setText(this.getHomeController().getFirstUser().getUsername());
    }

    @FXML
    public void onNewGameClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getModel());
    }

    @FXML
    public void onSettingsClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getModel());
    }

    @FXML
    public void onHistoryClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getModel());
    }

    @FXML
    public void onLeaderboardClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.LEADERBOARD, this.getController().getModel());
    }

}
