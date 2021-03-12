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
    private Button playButton;

    @Override
    public void init() {
        this.timersChoices.getItems().addAll(DefaultsTimers.values());
        this.timersChoices.setValue(DefaultsTimers.TEN_MINUTES);

        if (this.getHomeController().getNameGameTypeSelected().isEmpty()) {
            this.playButton.setDisable(true);
        }

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
    public void logPlayerOne(final Event event) throws IOException {
        if (!this.getHomeController().getFirstUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
        }
    }

    @FXML
    public void logPlayerTwo(final Event event) throws IOException {
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
    public void loadMatch() throws IOException, ClassNotFoundException {
//        final List<Board> loadedMatch = this.getHomeController().loadMatch();
//        this.getHomeController().createMatch();
        PageLoader.switchPage(this.getStage(), Pages.SAVED_HISTORY, this.getController().getModel());
    }

    @FXML
    public void playMatch(final Event event) throws IOException {
        this.getHomeController().setupPlayers();
        this.getHomeController().setTimer(this.timersChoices.getValue());
        this.getHomeController().createMatch();

        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getModel());
    }

}
