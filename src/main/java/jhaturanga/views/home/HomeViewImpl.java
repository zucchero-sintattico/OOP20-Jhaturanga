package jhaturanga.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.views.AbstractView;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @FXML
    private ChoiceBox<GameTypesEnum> gameModeChoices;

    @FXML
    private ChoiceBox<DefaultsTimers> timersChoices;

    @FXML
    private Label playerTextLable;

    @FXML
    void initialize() {
        this.gameModeChoices.getItems().addAll(GameTypesEnum.values());
        this.gameModeChoices.setValue(GameTypesEnum.CLASSIC_GAME);
        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.DIECI_MINUTI);

    }

    @Override
    public void init() {
        playerTextLable.setText(this.getHomeController().getUserNameLoggedUsers());

    }

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

}
