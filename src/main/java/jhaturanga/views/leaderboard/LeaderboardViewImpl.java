package jhaturanga.views.leaderboard;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import jhaturanga.controllers.leaderboard.LeaderboardController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LeaderboardViewImpl extends AbstractView implements LeaderboardView {

    @FXML
    private TableView<String> table;

    @Override
    public void init() {

    }

    @Override
    public LeaderboardController getLeaderboardController() {
        return (LeaderboardController) this.getController();
    }

    @FXML
    public void goBack(final ActionEvent event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }
}

