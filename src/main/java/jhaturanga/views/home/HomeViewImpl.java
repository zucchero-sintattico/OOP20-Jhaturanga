package jhaturanga.views.home;

import java.io.IOException;
import java.util.function.Function;

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
    private Button createGameTypeButton;

    @FXML
    private Button logPlayerTwoButton;

    @FXML
    private Button playButton;

    @Override
    public void init() {
        if (this.timersChoices.getItems().isEmpty()) {
            this.timersChoices.getItems().addAll(DefaultsTimers.values());
        }
        this.timersChoices.setValue(DefaultsTimers.TEN_MINUTES);
        this.setupGameTypeButtons();
        this.setUpPlayerLoginButtons();
    }

    private void setupGameTypeButtons() {
        this.playButton.setDisable(
                !this.getHomeController().isGameTypePresent() && !this.getHomeController().isDynamicGameTypePresent());
        this.createGameTypeButton.setDisable(this.getHomeController().isGameTypePresent());
        this.typeMenuButton.setDisable(this.getHomeController().isDynamicGameTypePresent());

        this.typeMenuButton.setText(
                this.getHomeController().getGameTypeName().map(Function.identity()).orElse("Select Game Type"));
    }

    private void setUpPlayerLoginButtons() {

        /**
         * Because of the unfortunate slight difference in these two conditional
         * statements it was inconvenient to try to wrap them with a common behaviour.
         */
        if (!this.getHomeController().isFirstUserLogged()
                || this.getHomeController().getFirstUser().get().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            this.logPlayerTwoButton.setDisable(true);
        } else {
            this.logPlayerOneButton.setText("LogOut");
        }
        if (!this.getHomeController().isSecondUserLogged()
                || this.getHomeController().getSecondUser().get().equals(UsersManager.GUEST)) {
            this.getHomeController().setSecondUserGuest();
        } else {
            this.logPlayerTwoButton.setText("LogOut");
        }

        this.getHomeController().getFirstUser().ifPresentOrElse(
                user -> this.playerOneLabel.setText("PLAYER ONE: " + user.getUsername()),
                () -> this.playerOneLabel.setText("PLAYER ONE: "));

        this.getHomeController().getSecondUser().ifPresentOrElse(
                user -> this.playerTwoLabel.setText("PLAYER TWO: " + user.getUsername()),
                () -> this.playerTwoLabel.setText("PLAYER TWO: "));

    }

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

    @FXML
    public void logPlayerOne(final Event event) throws IOException {
        if (this.getHomeController().getFirstUser().isPresent()
                && !this.getHomeController().getFirstUser().get().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
        }
    }

    @FXML
    public void deleteSelections(final Event event) throws IOException {
        this.getHomeController().getModel().clearMatchInfo();
        this.init();
    }

    @FXML
    public void logPlayerTwo(final Event event) throws IOException {
        if (this.getHomeController().getSecondUser().isPresent()
                && !this.getHomeController().getSecondUser().get().equals(UsersManager.GUEST)) {
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
    public void openEditor(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.EDITOR, this.getController().getModel());
    }

    @FXML
    public void openSettings(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getModel());
    }

    @FXML
    public void loadMatch() throws IOException, ClassNotFoundException {
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
