package jhaturanga.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.views.AbstractView;

public final class HomeViewImpl extends AbstractView implements HomeView {

    @FXML
    private ChoiceBox<GameTypesEnum> gameModeChoices;
    @FXML
    private Label playerTextLable;

    @FXML
    void initialize() {
        this.gameModeChoices.getItems().addAll(GameTypesEnum.values());
        this.gameModeChoices.setValue(GameTypesEnum.CLASSIC_GAME);
    }

    @Override
    public void init() {
        System.out.println(this.getController().getModel().getLoggedUsers().toString());
        this.playerTextLable.setText(this.getController().getModel().getLoggedUsers().toString());
    }

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

}
