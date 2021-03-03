package jhaturanga.views.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.game.MatchController;
import jhaturanga.controllers.game.MatchControllerImpl;
import jhaturanga.controllers.home.HomeController;
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
    private Label playerTextLable;

    @FXML
    private Button secondPlayerButton;

    @FXML
    private Button typeMenuButton;

    @FXML
    private Button playButton;

    @FXML
    void initialize() {

        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.DIECI_MINUTI);

    }

    @Override
    public void init() {

        if (this.getHomeController().getUsers().size() == 0) {
            this.getHomeController().addUser(UsersManager.GUEST);
            this.getHomeController().addUser(UsersManager.GUEST);
        }
        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.getHomeController().getUsers().get(0));
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.getHomeController().getUsers().get(1));
        this.getHomeController().setBlackPlayer(blackPlayer);
        this.getHomeController().setWhitePlayer(whitePlayer);
        playerTextLable.setText(this.getHomeController().getUserNameLoggedUsers());
        this.secondPlayerButton.setDisable(this.getHomeController().getNumbersOfLoggedUser() >= 2);
        if (this.getHomeController().getNameGameTypeSelected().equals(Optional.empty())) {
            this.typeMenuButton.setText("seleziona");
        } else {
            this.typeMenuButton.setText(this.getHomeController().getNameGameTypeSelected().get());
        }
    }

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

    @FXML
    void logSecondPlayer(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());

    }

    @FXML
    void gameTypeMenuButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getModel());

    }

    @FXML
    void playMatch(final Event event) throws IOException {
        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.getHomeController().getUsers().get(0));
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.getHomeController().getUsers().get(1));
        this.getHomeController().setTimer(Optional.of(new TimerFactoryImpl()
                .equalTime(List.of(whitePlayer, blackPlayer), timersChoices.getValue().getSeconds())));
        this.getHomeController().setWhitePlayer(whitePlayer);
        this.getHomeController().setBlackPlayer(blackPlayer);
        final MatchController controller = new MatchControllerImpl();
        controller.setModel(this.getController().getModel());
        this.getHomeController().createMatch();

        // System.out.println(this.getController().getModel().getActualMatch().get());
        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

}
