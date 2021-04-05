package jhaturanga.views.leaderboard;

import java.io.IOException;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jhaturanga.controllers.leaderboard.LeaderboardController;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class LeaderboardView extends AbstractJavaFXView {

    @FXML
    private TableView<LeaderboardUserAdapter> table;

    @Override
    public void init() {

        final TableColumn<LeaderboardUserAdapter, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(u -> new SimpleStringProperty(u.getValue().getUsername()));

        final TableColumn<LeaderboardUserAdapter, Integer> winColumn = new TableColumn<>("Wins");
        winColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getWinCount()).asObject());

        final TableColumn<LeaderboardUserAdapter, Integer> drawColumn = new TableColumn<>("Draws");
        drawColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getDrawCount()).asObject());

        final TableColumn<LeaderboardUserAdapter, Integer> loseColumn = new TableColumn<>("Loses");
        loseColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getLostCount()).asObject());

        final TableColumn<LeaderboardUserAdapter, Integer> totalMatchColumn = new TableColumn<>("Total Match");
        totalMatchColumn
                .setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getPlayedMatchCount()).asObject());

        final TableColumn<LeaderboardUserAdapter, Integer> score = new TableColumn<>("Score");
        score.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getScore()).asObject());

        table.getColumns().add(nameColumn);
        table.getColumns().add(winColumn);
        table.getColumns().add(drawColumn);
        table.getColumns().add(loseColumn);
        table.getColumns().add(totalMatchColumn);
        table.getColumns().add(score);

        try {
            this.table.setItems(new ObservableListWrapper<>(this.getLeaderboardController().getUsers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LeaderboardController getLeaderboardController() {
        return (LeaderboardController) this.getController();
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }
}
