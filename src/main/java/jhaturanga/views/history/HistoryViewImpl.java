package jhaturanga.views.history;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jhaturanga.model.replay.Replay;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class HistoryViewImpl extends AbstractJavaFXView implements HistoryView {

    @FXML
    private VBox mainList;

    private void addSavedMatchToMainList(final Replay boardState) {
        final Button playButton = new Button("View Replay");
        playButton.setOnMouseClicked((e) -> {
            this.getHistoryController().play(boardState);
            PageLoader.switchPage(this.getStage(), Pages.REPLAY, this.getController().getApplicationInstance());

        });
        this.mainList.getChildren()
                .addAll(new Text(boardState.getWhiteUser().getUsername() + "," + boardState.getBlackUser().getUsername()
                        + "," + boardState.getDate() + "," + boardState.getGameType()), playButton);
    }

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());

        this.getHistoryController().getAllSavedMatchDataOrder().forEach(this::addSavedMatchToMainList);
    }

    @FXML
    public void onBackClick() {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

}
