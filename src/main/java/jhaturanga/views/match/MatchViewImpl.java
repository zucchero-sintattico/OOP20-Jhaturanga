package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.timer.ObservableTimer;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;
import jhaturanga.views.history.HistoryBoard;

public final class MatchViewImpl extends AbstractView implements MatchView {

    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane grid;

    @FXML
    private Label timerP1;

    @FXML
    private Label timerP2;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    public void initialize() {

    }

    private void onTimeChange() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timerP1.setText(getGameController().getWhiteReminingTime());
                timerP2.setText(getGameController().getBlackReminingTime());
            }
        });

    }

    private void onTimeFinish() {
        Platform.runLater(() -> {
            final EndGamePopup popup = new EndGamePopup();
            popup.setMessage("Tempo finito");
            popup.setButtonAction(() -> {
                this.backToMainMenu();
                popup.close();
            });
            popup.show();
        });
    }

    @Override
    public void init() {
        this.getGameController().start();

        // final Pane board = new BoardView(this.getGameController(), this);
        final Pane board = new HistoryBoard(this.getGameController());

        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.setCenter(board);
        this.getController().getModel().getTimer().ifPresent(t -> {
            new ObservableTimer(t, this::onTimeFinish, this::onTimeChange).start();
        });
        this.player1Label.setText(this.getGameController().getModel().getWhitePlayer().getUser().getUsername());
        this.player2Label.setText(this.getGameController().getModel().getBlackPlayer().getUser().getUsername());
    }

    @Override
    public MatchController getGameController() {
        return (MatchController) this.getController();
    }

    @FXML
    public void giveUpMatch(final Event event) {
        this.getGameController().getModel().getTimer().get().stop();
        this.getGameController().getModel().clearMatchInfo();
        Platform.runLater(() -> {
            final EndGamePopup popup = new EndGamePopup();
            popup.setMessage(
                    this.getGameController().getPlayerTurn().getUser().getUsername() + " are you sure to give up?");
            popup.setButtonAction(() -> {
                this.backToMainMenu();
                popup.close();
            });
            popup.show();
        });

    }

    @FXML
    public void backToMenu(final Event event) throws IOException {
        this.backToMainMenu();
    }

    private void backToMainMenu() {
        this.getGameController().getModel().clearMatchInfo();
        this.getGameController().getModel().getTimer().get().stop();
        try {
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveMatch(final Event event) throws IOException {
        this.getGameController().saveMatch();
    }

}
