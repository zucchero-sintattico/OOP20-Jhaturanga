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
    public void initialize() {
        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.TEN_MINUTES);
    }

    @Override
    public void init() {

        if (!this.getHomeController().isFirstUserLogged()
                || this.getHomeController().getFirstUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            this.logPlayerTwoButton.setDisable(true);
        } else {
            this.logPlayerOneButton.setText("LogOut");
        }
        if (!this.getHomeController().isSecondUserLogged()
                || this.getHomeController().getSecondUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setSecondUserGuest();
        } else {
            this.logPlayerTwoButton.setText("LogOut");
        }
        if (this.getController().getModel().getBlackPlayer() == null
                && this.getController().getModel().getWhitePlayer() == null) {
            final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.getHomeController().getFirstUser());
            final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.getHomeController().getSecondUser());
            System.out.println("STO SETTANDO I PLAYER");
            this.getHomeController().setBlackPlayer(blackPlayer);
            this.getHomeController().setWhitePlayer(whitePlayer);
        }
        this.playerOneLabel.setText("PLAYER ONE: " + this.getHomeController().getFirstUser().getUsername());
        this.playerTwoLabel.setText("PLAYER TWO: " + this.getHomeController().getSecondUser().getUsername());
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
        if (!this.getHomeController().getFirstUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
        }
    }

    @FXML
    void logPlayerTwo(final Event event) throws IOException {
        if (!this.getHomeController().getSecondUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setSecondUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
        }

    }

    @FXML
    public void gameTypeMenuButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getModel());

    }

    @FXML
    public void openSettings(final Event event) throws IOException {
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
        this.getHomeController().setTimer(this.timersChoices.getValue());

        this.getHomeController().createMatch();

        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

}
