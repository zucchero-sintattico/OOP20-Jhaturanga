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
import jhaturanga.model.user.User;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class LeaderboardViewImpl extends AbstractJavaFXView implements LeaderboardView {

    @FXML
    private TableView<User> table;

    @Override
    public void init() {

        final TableColumn<User, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(u -> new SimpleStringProperty(u.getValue().getUsername()));

        final TableColumn<User, Integer> winColumn = new TableColumn<>("Win");
        winColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getWinCount()).asObject());

        final TableColumn<User, Integer> loseColumn = new TableColumn<>("Lose");
        loseColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getLostCount()).asObject());

        final TableColumn<User, Integer> drawColumn = new TableColumn<>("Draw");
        drawColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getDrawCount()).asObject());

        final TableColumn<User, Integer> totalMatchColumn = new TableColumn<>("Total Match");
        totalMatchColumn.setCellValueFactory(u -> new SimpleIntegerProperty(u.getValue().getPlayedMatchCount()).asObject());

        table.getColumns().add(nameColumn);
        table.getColumns().add(winColumn);
        table.getColumns().add(loseColumn);
        table.getColumns().add(drawColumn);
        table.getColumns().add(totalMatchColumn);
        try {
            this.table.setItems(new ObservableListWrapper<>(this.getLeaderboardController().getUsers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LeaderboardController getLeaderboardController() {
        return (LeaderboardController) this.getController();
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }
}
