package jhaturanga.views.home;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @FXML
    private ChoiceBox<GameTypesEnum> gameModeChoices;

    @FXML
    private ChoiceBox<DefaultsTimers> timersChoices;

    @FXML
    private Label playerTextLable;

    @FXML
    private Button secondPlayerButton;

    @FXML
    void initialize() {
        this.gameModeChoices.getItems().addAll(GameTypesEnum.values());
        this.gameModeChoices.setValue(GameTypesEnum.CLASSIC_GAME);
        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.DIECI_MUNUTI);

    }

    @Override
    public void init() {
        playerTextLable.setText(this.getHomeController().getUserNameLoggedUsers());
        this.secondPlayerButton.setDisable(this.getHomeController().getNumbersOfLoggedUser() >= 2);

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
    void settingButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getModel());
    }

}
