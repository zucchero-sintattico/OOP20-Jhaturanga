package jhaturanga.views.savedhistory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jhaturanga.controllers.savedhistory.SavedHistoryController;
import jhaturanga.views.AbstractView;

public final class HistoryViewImpl extends AbstractView implements HistoryView {

    @FXML
    private VBox mainList;

    @Override
    public void init() {
        this.getSavedHistoryController().getAllSavedMatchDataOrder().forEach(board -> {
            final Button playButton = new Button("PLAY");
            playButton.setOnMouseClicked((e) -> {
                this.getSavedHistoryController().play(board);
            });
            this.mainList.getChildren().addAll(new Text(board.getWhiteUser().getUsername() + ","
                    + board.getBlackUser().getUsername() + "," + board.getDate() + "\n"), playButton);

        }

        );
    }

    @Override
    public SavedHistoryController getSavedHistoryController() {
        return (SavedHistoryController) this.getController();
    }

}
