package jhaturanga.views.leaderboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.Pages;
import jhaturanga.views.pages.PageLoader;

public final class LeaderBoardViewImpl extends AbstractView implements LeaderBoardView {

    @Override
    public void init() {

    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

}
