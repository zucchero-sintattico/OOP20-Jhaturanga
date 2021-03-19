package jhaturanga.views.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractView;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());
    }

    @FXML
    public void onNewGameClick(final ActionEvent event) {
        System.out.println("PLAY");
    }

    @FXML
    public void onSettingsClick(final ActionEvent event) {
        System.out.println("SETTINGS");
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
