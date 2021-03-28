package jhaturanga.views.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.user.User;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic Implementation of Home Page View.
 */
public final class HomeView extends AbstractJavaFXView {

    @FXML
    private Label usernameLabel;

    @Override
    public void init() {
        this.getHomeController().getFirstUser().map(User::getUsername).ifPresent(this.usernameLabel::setText);
    }

    @FXML
    public void onNewGameClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getApplicationInstance());
    }

    @FXML
    public void onSettingsClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getApplicationInstance());
    }

    @FXML
    public void onHistoryClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getApplicationInstance());
    }

    @FXML
    public void onLeaderboardClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.LEADERBOARD, this.getController().getApplicationInstance());
    }

    private HomeController getHomeController() {
        return (HomeController) this.getController();
    }

}
