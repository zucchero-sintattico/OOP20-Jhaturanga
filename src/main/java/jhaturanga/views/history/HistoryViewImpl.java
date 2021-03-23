package jhaturanga.views.history;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jhaturanga.controllers.history.HistoryController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class HistoryViewImpl extends AbstractJavaFXView implements HistoryView {

    @FXML
    private VBox mainList;

    @Override
    public void init() {
        this.getStage().setMinHeight(this.getStage().getHeight());
        this.getStage().setMinWidth(this.getStage().getWidth());
        this.getSavedHistoryController().getAllSavedMatchDataOrder().forEach(board -> {
            final Button playButton = new Button("View Replay");
            playButton.setOnMouseClicked((e) -> {
                this.getSavedHistoryController().play(board);
                PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getApplicationInstance());

            });
            this.mainList.getChildren().addAll(new Text(board.getWhiteUser().getUsername() + ","
                    + board.getBlackUser().getUsername() + "," + board.getDate() + "," + board.getGameType()),
                    playButton);

        });
    }

    @FXML
    public void backToMenu(final Event event) throws IOException {
        this.backToMainMenu();
    }

    private void backToMainMenu() {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    @Override
    public HistoryController getSavedHistoryController() {
        return (HistoryController) this.getController();
    }

}
