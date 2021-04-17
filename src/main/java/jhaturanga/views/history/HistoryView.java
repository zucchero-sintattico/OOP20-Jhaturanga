package jhaturanga.views.history;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import jhaturanga.controllers.history.HistoryController;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View that shows all the saved match for replaying.
 */
public final class HistoryView extends AbstractJavaFXView {

    @FXML
    private TableView<ReplayData> table;

    @FXML
    private VBox mainList;

    @Override
    public void init() {
        final TableColumn<ReplayData, String> gameTypeColumn = new TableColumn<>("Game Type");
        gameTypeColumn
                .setCellValueFactory(replay -> new SimpleStringProperty(replay.getValue().getGameType().getName()));

        final TableColumn<ReplayData, String> whitePlayerUsernameColumn = new TableColumn<>("White Player");
        whitePlayerUsernameColumn.setCellValueFactory(
                replay -> new SimpleStringProperty(replay.getValue().getWhiteUser().getUsername()));

        final TableColumn<ReplayData, String> blackPlayerusernameColumn = new TableColumn<>("Black Player");
        blackPlayerusernameColumn.setCellValueFactory(
                replay -> new SimpleStringProperty(replay.getValue().getBlackUser().getUsername()));

        final TableColumn<ReplayData, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(
                replay -> new SimpleStringProperty(this.getFormattedDate(replay.getValue().getDate())));

        final TableColumn<ReplayData, Button> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellValueFactory(replay -> this.generateReplayStartButton(replay.getValue()));

        this.table.getColumns().add(gameTypeColumn);
        this.table.getColumns().add(whitePlayerUsernameColumn);
        this.table.getColumns().add(blackPlayerusernameColumn);
        this.table.getColumns().add(dateColumn);
        this.table.getColumns().add(buttonColumn);

        this.table.setItems(new ObservableListWrapper<>(this.getHistoryController().getAllSavedReplaysOrdered()));
    }

    private String getFormattedDate(final Date date) {
        return new SimpleDateFormat("yyyy/MM/dd - hh:mm", Locale.ITALY).format(date);
    }

    private Property<Button> generateReplayStartButton(final ReplayData replayData) {
        final Button playButton = new Button("View Replay");
        playButton.setOnMouseClicked((e) -> {
            this.getHistoryController().setReplay(replayData);
            PageLoader.getInstance().switchPage(this.getStage(), Pages.REPLAY,
                    this.getController().getApplicationInstance());

        });
        return new SimpleObjectProperty<>(playButton);
    }

    private void addSavedMatchToMainList(final ReplayData boardState) {
        final Button playButton = new Button("View Replay");
        playButton.setOnMouseClicked((e) -> {
            this.getHistoryController().setReplay(boardState);
            PageLoader.getInstance().switchPage(this.getStage(), Pages.REPLAY,
                    this.getController().getApplicationInstance());

        });
        final HBox row = new HBox(10.0);
        row.setAlignment(Pos.CENTER);
        final Text replayDescription = new Text(
                boardState.getWhiteUser().getUsername() + "," + boardState.getBlackUser().getUsername() + ","
                        + boardState.getDate() + "," + boardState.getGameType());
        replayDescription.setFill(Color.WHITE);
        row.getChildren().add(replayDescription);
        row.getChildren().add(playButton);
        this.mainList.getChildren().add(row);
    }

    @FXML
    public void onBackClick() {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    private HistoryController getHistoryController() {
        return (HistoryController) this.getController();
    }

}
