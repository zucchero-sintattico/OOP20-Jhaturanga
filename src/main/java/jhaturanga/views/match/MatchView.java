package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.timer.TimerThread;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.commons.board.MatchBoard;
import jhaturanga.views.commons.component.EndGamePopup;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View where the user play a match.
 */
public final class MatchView extends AbstractJavaFXView {

    private static final int SECONDS_IN_ONE_MINUTE = 60;

    @FXML
    private Label firstPlayerUsername;

    @FXML
    private Label firstPlayerRemainingTime;

    @FXML
    private Label secondPlayerUsername;

    @FXML
    private Label secondPlayerRemainingTime;

    @FXML
    private Label gameTypeName;

    @FXML
    private Label gameTypeDescription;

    @FXML
    private HBox topBar;

    @FXML
    private HBox bottomBar;

    @FXML
    private StackPane boardContainer;

    private MatchBoard board;

    @Override
    public void init() {
        this.getMatchController().start();
        this.setupGameTypeInfo();
        this.setupPlayersInfo();

        this.board = new MatchBoard(this.getMatchController(), this::onMatchEnd);
        this.board.setup();

        this.board.maxWidthProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));
        this.board.maxHeightProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));

        this.topBar.prefWidthProperty().bind(this.board.widthProperty());
        this.topBar.maxWidthProperty().bind(this.board.widthProperty());
        this.bottomBar.prefWidthProperty().bind(this.board.widthProperty());
        this.bottomBar.maxWidthProperty().bind(this.board.widthProperty());

        this.boardContainer.getChildren().add(this.board);

        new TimerThread(this.getMatchController().getTimer(), this::onTimeFinish, this::onTimeChange).start();

        this.updateTimerLabels();

        this.getStage().setOnCloseRequest(e -> this.checkIfTimerIsPresentAndStopIt());
    }

    private void setupPlayersInfo() {
        this.firstPlayerUsername.setText(this.getMatchController().getWhitePlayer().getUser().getUsername());
        this.secondPlayerUsername.setText(this.getMatchController().getBlackPlayer().getUser().getUsername());
    }

    private void setupGameTypeInfo() {
        this.getMatchController().getApplicationInstance().getMatch()
                .ifPresent(match -> this.gameTypeName.setText(match.getGame().getType().getName()));
        this.getMatchController().getApplicationInstance().getMatch()
                .ifPresent(match -> this.gameTypeDescription.setText(match.getGame().getType().getDescription()));
    }

    private void checkIfTimerIsPresentAndStopIt() {
        if (this.getMatchController().isMatchPresent()) {
            this.getMatchController().getTimer().stop();
        }
    }

    /**
     * Get the board for testing purpose.
     * 
     * @return the board view
     */
    public MatchBoard getBoardView() {
        return this.board;
    }

    private void updateTimerLabels() {
        final String bigger = "-fx-font-size: 36px";
        final String smaller = "-fx-font-size: 18px";
        if (this.getMatchController().getPlayerTurn().getColor().equals(PlayerColor.WHITE)) {
            this.firstPlayerUsername.setStyle(bigger);
            this.firstPlayerRemainingTime.setStyle(bigger);
            this.secondPlayerUsername.setStyle(smaller);
            this.secondPlayerRemainingTime.setStyle(smaller);
        } else {
            this.firstPlayerUsername.setStyle(smaller);
            this.firstPlayerRemainingTime.setStyle(smaller);
            this.secondPlayerUsername.setStyle(bigger);
            this.secondPlayerRemainingTime.setStyle(bigger);
        }
        this.firstPlayerRemainingTime
                .setText(this.secondsToHumanReadableTime(this.getMatchController().getWhiteRemainingTime()));
        this.secondPlayerRemainingTime
                .setText(this.secondsToHumanReadableTime(this.getMatchController().getBlackRemainingTime()));
    }

    private String secondsToHumanReadableTime(final double seconds) {
        if (Double.isInfinite(seconds)) {
            return "no limit";
        }
        final double minutes = seconds / SECONDS_IN_ONE_MINUTE;
        final double secondsFromMinutes = seconds % SECONDS_IN_ONE_MINUTE;
        return String.format("%02d:%02d", (int) minutes, (int) secondsFromMinutes);
    }

    private void openEndGamePopup() {
        final EndGamePopup popup = new EndGamePopup();
        final String preamble = "Game ended for " + this.getMatchController().getEndType().get().toString() + "\n";
        final String message = this.getMatchController().getWinner().isPresent()
                ? "The Winner is " + this.getMatchController().getWinner().get().getUser().getUsername()
                : "";
        popup.setMessage(preamble + message);
        popup.setButtonAction(() -> {
            this.getMatchController().deleteMatch();
            popup.close();
            Platform.runLater(() -> PageLoader.getInstance().switchPage(this.getStage(), Pages.HOME,
                    this.getController().getApplicationInstance()));
        });
        popup.show();
    }

    private void onMatchEnd() {
        try {
            this.getMatchController().saveMatch();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.getMatchController().getTimer().stop();
        this.openEndGamePopup();
    }

    private void onTimeChange() {
        Platform.runLater(this::updateTimerLabels);
    }

    private void onTimeFinish() {
        Platform.runLater(this::onMatchEnd);
    }

    @FXML
    public void onResignClick(final ActionEvent event) {
        Platform.runLater(() -> {
            if (this.getMatchController().isMatchPresent()) {
                final EndGamePopup popup = new EndGamePopup();
                popup.setMessage(this.getMatchController().getPlayerTurn().getUser().getUsername()
                        + " are you sure to give up?");
                popup.setButtonAction(() -> {
                    this.getMatchController().resign(this.getMatchController().getPlayerTurn());
                    this.onMatchEnd();
                    popup.close();
                });
                popup.show();
            }
        });

    }

    private MatchController getMatchController() {
        return (MatchController) this.getController();
    }
}
