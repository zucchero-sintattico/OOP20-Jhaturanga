package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jhaturanga.commons.Pair;
import jhaturanga.commons.graphics.EndGamePopup;
import jhaturanga.commons.graphics.MatchBoardView;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.ObservableTimer;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class MatchViewImpl extends AbstractJavaFXView implements MatchView {

    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane grid;

    @FXML
    private Label timerP1;

    @FXML
    private Button saveMatchButton;

    @FXML
    private Label timerP2;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    private void updateTimerLabels() {
        timerP1.setText(getMatchController().getWhiteReminingTime());
        timerP2.setText(getMatchController().getBlackReminingTime());
    }

    private void openEndGamePopup() {
        final EndGamePopup popup = new EndGamePopup();
        popup.setMessage("Tempo finito");
        popup.setButtonAction(() -> {
            this.backToMainMenu();
            popup.close();
        });
        popup.show();
    }

    private void onTimeChange() {
        Platform.runLater(this::updateTimerLabels);
    }

    private void onTimeFinish() {
        Platform.runLater(this::openEndGamePopup);
    }

    @Override
    public void init() {

        this.getMatchController().start();

        final Pane board = new MatchBoardView(this.getMatchController(), this);
        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.setCenter(board);

        // TODO: CAMBIARE NOME
        new ObservableTimer(this.getMatchController().getTimer(), this::onTimeFinish, this::onTimeChange).start();

        final Pair<Player, Player> players = this.getMatchController().getPlayers();
        this.player1Label.setText(players.getX().getUser().getUsername());
        this.player2Label.setText(players.getY().getUser().getUsername());
    }

    @FXML
    public void giveUpMatch(final Event event) {
        this.getMatchController().getTimer().stop();
        this.getMatchController().deleteMatch();
        Platform.runLater(() -> {
            final EndGamePopup popup = new EndGamePopup();
            popup.setMessage(
                    this.getMatchController().getPlayerTurn().getUser().getUsername() + " are you sure to give up?");
            popup.setButtonAction(() -> {
                this.backToMainMenu();
                popup.close();
            });
            popup.show();
        });

    }

    @FXML
    public void backToMenu(final Event event) throws IOException {
        this.saveMatch(event);
        this.backToMainMenu();
    }

    private void backToMainMenu() {
        this.getMatchController().deleteMatch();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    @FXML
    public void saveMatch(final Event event) throws IOException {
        this.getMatchController().saveMatch();
    }

}
