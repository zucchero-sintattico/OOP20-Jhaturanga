package jhaturanga.views.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.board.Board;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @FXML
    private ChoiceBox<DefaultsTimers> timersChoices;

    @FXML
    private Label playerOneLabel;

    @FXML
    private Label playerTwoLabel;

    @FXML
    private Button typeMenuButton;

    @FXML
    private Button logPlayerOneButton;

    @FXML
    private Button logPlayerTwoButton;

    @FXML
    void initialize() {
        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.TEN_MINUTES);
    }

    @Override
    public void init() {

        if (this.getHomeController().getUsers().size() == 0) {
            this.getHomeController().addUser(UsersManager.GUEST);
            this.getHomeController().addUser(UsersManager.GUEST);
        }
        if (this.getController().getModel().getBlackPlayer() == null
                && this.getController().getModel().getWhitePlayer() == null) {
            final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.getHomeController().getUsers().get(0));
            final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.getHomeController().getUsers().get(1));
            this.getHomeController().setBlackPlayer(blackPlayer);
            this.getHomeController().setWhitePlayer(whitePlayer);
        }
        this.playerOneLabel.setText("PLAYER ONE: " + this.getHomeController().getUserNameLoggedUsers().get(0));
        this.playerTwoLabel.setText("PLAYER TWO: " + this.getHomeController().getUserNameLoggedUsers().get(1));
        if (!this.getHomeController().getNameGameTypeSelected().equals(Optional.empty())) {
            this.typeMenuButton.setText(this.getHomeController().getNameGameTypeSelected().get());
        }

    }

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

    @FXML
    void logPlayerOne(final Event event) throws IOException {
        if (this.getHomeController().getNumbersOfLoggedUser() > 0) {
            this.getHomeController().logOut();
        }

        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());

    }

    @FXML
    void logPlayerTwo(final Event event) throws IOException {
        if (this.getHomeController().getNumbersOfLoggedUser() > 1) {
            this.getHomeController().logOut();
        }

        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());

    }

    @FXML
    void gameTypeMenuButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getModel());

    }

    @FXML
    void openSettings() throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getModel());
    }

    @FXML
    void loadMatch() throws IOException, ClassNotFoundException {
        final List<Board> loadedMatch = this.getHomeController().loadMatch();
        this.getHomeController().createMatch();
        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

    @FXML
    void playMatch(final Event event) throws IOException {

        if (this.timersChoices.getValue().getIncrement().isPresent()) {
            this.getHomeController()
                    .setTimer(new TimerFactoryImpl().incrementableTimer(this.getHomeController().getPlayer(),
                            this.timersChoices.getValue().getSeconds(),
                            this.timersChoices.getValue().getIncrement().get()));
        } else {
            this.getHomeController().setTimer(new TimerFactoryImpl().equalTimer(this.getHomeController().getPlayer(),
                    timersChoices.getValue().getSeconds()));
        }
        this.getHomeController().createMatch();

        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

}
