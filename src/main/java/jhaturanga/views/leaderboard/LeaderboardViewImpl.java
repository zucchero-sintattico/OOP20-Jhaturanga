package jhaturanga.views.leaderboard;

import java.io.IOException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jhaturanga.controllers.leaderboard.LeaderboardController;
import jhaturanga.model.user.User;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LeaderboardViewImpl extends AbstractView implements LeaderboardView {

    @FXML
    private TableView<User> table;

    @Override
    public void init() {

        final TableColumn<User, String> nameColumn = new TableColumn<>("user");
        nameColumn.setCellValueFactory(u -> new ReadOnlyStringWrapper(u.getValue().getUsername()));

        final TableColumn<User, String> winColumn = new TableColumn<>("win");
        winColumn.setCellValueFactory(u -> new ReadOnlyStringWrapper(String.valueOf(u.getValue().getWinCount())));

        final TableColumn<User, String> loseColumn = new TableColumn<>("lose");
        loseColumn.setCellValueFactory(u -> new ReadOnlyStringWrapper(String.valueOf(u.getValue().getLostCount())));

        final TableColumn<User, String> drawColumn = new TableColumn<>("draw");
        drawColumn.setCellValueFactory(u -> new ReadOnlyStringWrapper(String.valueOf(u.getValue().getDrawCount())));

        table.getColumns().add(nameColumn);
        table.getColumns().add(winColumn);
        table.getColumns().add(loseColumn);
        table.getColumns().add(drawColumn);
        try {
            this.table.setItems(new ObservableListWrapper<>(this.getLeaderboardController().getUsers()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public LeaderboardController getLeaderboardController() {
        return (LeaderboardController) this.getController();
    }

    @FXML
    public void goBack(final ActionEvent event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }
}
