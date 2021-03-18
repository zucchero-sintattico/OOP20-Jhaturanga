package jhaturanga.views.savedhistory;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jhaturanga.controllers.savedhistory.SavedHistoryController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class HistoryViewImpl extends AbstractView implements HistoryView {

    @FXML
    private VBox mainList;

    @Override
    public void init() {
        this.getSavedHistoryController().getAllSavedMatchDataOrder().forEach(board -> {
            final Button playButton = new Button("View Replay");
            playButton.setOnMouseClicked((e) -> {
                this.getSavedHistoryController().play(board);
                PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getModel());

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
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public SavedHistoryController getSavedHistoryController() {
        return (SavedHistoryController) this.getController();
    }

}
