package jhaturanga.views.game;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import jhaturanga.controllers.oldhome.OldHomeController;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class GameViewImpl extends AbstractView implements GameView {

    @FXML
    private ChoiceBox<DefaultTimers> timersChoices;

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
            this.timersChoices.getItems().addAll(DefaultTimers.values());
        }
        this.timersChoices.setValue(DefaultTimers.TEN_MINUTES);
        this.setupGameTypeButtons();
        this.setUpPlayerLoginButtons();
    }

    private void setupGameTypeButtons() {
        this.playButton.setDisable(
                !this.getHomeController().isGameTypePresent() && !this.getHomeController().isDynamicGameTypePresent());
        this.createGameTypeButton.setDisable(this.getHomeController().isGameTypePresent());
        this.typeMenuButton.setDisable(this.getHomeController().isDynamicGameTypePresent());
        this.typeMenuButton
                .setText(this.getHomeController().isGameTypePresent() ? this.getHomeController().getGameTypeName()
                        : "Select Game Type");
    }

    private void setUpPlayerLoginButtons() {
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
    }

    @Override
    public OldHomeController getHomeController() {
        return (OldHomeController) this.getController();
    }

    @FXML
    public void logPlayerOne(final Event event) throws IOException {
        if (!this.getHomeController().getFirstUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setFirstUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getApplicationInstance());
        }
    }

    @FXML
    public void deleteSelections(final Event event) throws IOException {
        this.getHomeController().getApplicationInstance().clearMatchInfo();
        this.init();
    }

    @FXML
    public void logPlayerTwo(final Event event) throws IOException {
        if (!this.getHomeController().getSecondUser().equals(UsersManager.GUEST)) {
            this.getHomeController().setSecondUserGuest();
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
        } else {
            PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getApplicationInstance());
        }

    }

    @FXML
    public void gameTypeMenuButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getApplicationInstance());
    }

    @FXML
    public void openEditor(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.EDITOR, this.getController().getApplicationInstance());
    }

    @FXML
    public void openSettings(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getApplicationInstance());
    }

    @FXML
    public void loadMatch() throws IOException, ClassNotFoundException {
        PageLoader.switchPage(this.getStage(), Pages.SAVED_HISTORY, this.getController().getApplicationInstance());
    }

    @FXML
    public void playMatch(final Event event) throws IOException {
        this.getHomeController().setupPlayers();
        this.getHomeController().setTimer(this.timersChoices.getValue());
        this.getHomeController().createMatch();
        PageLoader.switchPage(this.getStage(), Pages.GAME, this.getController().getApplicationInstance());
    }

}
