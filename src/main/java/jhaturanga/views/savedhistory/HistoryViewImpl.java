package jhaturanga.views.savedhistory;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import jhaturanga.controllers.savedhistory.SavedHistoryController;
import jhaturanga.views.AbstractView;

public final class HistoryViewImpl extends AbstractView implements HistoryView {

    @FXML
    private StackPane mainList;

    @Override
    public void init() {
        this.getSavedHistoryController().getAllSavedMatchDataOrder().forEach(board -> {

            this.mainList.getChildren().add(new Text(board.getWhiteUser().getUsername() + ","
                    + board.getBlackUser().getUsername() + "," + board.getDate() + "\n"));

        }

        );
    }

    @Override
    public SavedHistoryController getSavedHistoryController() {
        return (SavedHistoryController) this.getController();
    }

}
