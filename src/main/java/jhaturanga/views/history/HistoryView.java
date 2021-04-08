package jhaturanga.views.history;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jhaturanga.controllers.history.HistoryController;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class HistoryView extends AbstractJavaFXView {

    @FXML
    private VBox mainList;

    @Override
    public void init() {
        this.getHistoryController().getAllSavedMatchDataOrder().forEach(this::addSavedMatchToMainList);
    }

    private void addSavedMatchToMainList(final ReplayData boardState) {
        final Button playButton = new Button("View Replay");
        playButton.setOnMouseClicked((e) -> {
            this.getHistoryController().setReplay(boardState);
            PageLoader.switchPage(this.getStage(), Pages.REPLAY, this.getController().getApplicationInstance());

        });
        this.mainList.getChildren()
                .addAll(new Text(boardState.getWhiteUser().getUsername() + "," + boardState.getBlackUser().getUsername()
                        + "," + boardState.getDate() + "," + boardState.getGameType()), playButton);
    }

    @FXML
    public void onBackClick() {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    private HistoryController getHistoryController() {
        return (HistoryController) this.getController();
    }

}
