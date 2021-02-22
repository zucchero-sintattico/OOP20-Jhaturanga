package jhaturanga.views.home;

import java.io.IOException;
import java.util.Optional;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.timer.DefaultsTimers;
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
        playerTextLable.setText(this.getHomeController().getUserNameLoggedUsers());
        this.secondPlayerButton.setDisable(this.getHomeController().getNumbersOfLoggedUser() >= 2);
        this.typeMenuButton.setText(this.getHomeController().getNameGameTypeSelected());

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
        this.getController().getModel().createMatch(this.getController().getModel().getGameType(), Optional.empty());
        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

}
