package jhaturanga.views.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());
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
        System.out.println("HISTORY");
    }

    @FXML
    public void onLeaderboardClick(final ActionEvent event) {
        System.out.println("LEADERBOARD");
    }

}
