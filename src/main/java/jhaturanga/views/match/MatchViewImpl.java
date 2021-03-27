package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.graphics.EndGamePopup;
import jhaturanga.commons.graphics.MatchBoard;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.timer.TimerThread;
import jhaturanga.views.AbstractJavaFXView;

public final class MatchViewImpl extends AbstractJavaFXView implements MatchView {

    private static final int SECONDS_IN_ONE_MINUTE = 60;

    @FXML
    private Label whitePlayerUsernameLabel;

    @FXML
    private Label whitePlayerRemainingTimeLabel;

    @FXML
    private Label blackPlayerUsernameLabel;

    @FXML
    private Label blackPlayerRemainingTimeLabel;

    @FXML
    private HBox topBar;

    @FXML
    private HBox bottomBar;

    @FXML
    private StackPane boardContainer;

    private MatchBoard board;

    @Override
    public void init() {

        this.getStage().setOnCloseRequest(null);
        this.getMatchController().start();

        this.whitePlayerUsernameLabel.setText(this.getMatchController().getWhitePlayer().getUser().getUsername());
        this.blackPlayerUsernameLabel.setText(this.getMatchController().getBlackPlayer().getUser().getUsername());

        this.board = new MatchBoard(this, this::onMatchEnd);

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
        this.whitePlayerRemainingTimeLabel
                .setText(this.secondsToHumanReadableTime(this.getMatchController().getWhiteReminingTime()));
        this.blackPlayerRemainingTimeLabel
                .setText(this.secondsToHumanReadableTime(this.getMatchController().getBlackReminingTime()));
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
        popup.setMessage("Game ended for " + this.getMatchController().matchStatus().toString());
        popup.setButtonAction(() -> {
            this.getMatchController().deleteMatch();

            popup.close();
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
        Platform.runLater(this::openEndGamePopup);
    }

    @FXML
    public void onResignClick(final ActionEvent event) {
        Platform.runLater(() -> {
            if (this.getMatchController().isMatchPresent()) {
                final EndGamePopup popup = new EndGamePopup();
                popup.setMessage(this.getMatchController().getPlayerTurn().getUser().getUsername()
                        + " are you sure to give up?");
                popup.setButtonAction(() -> {
                    this.onMatchEnd();
                    popup.close();
                });
                popup.show();
            }
        });

    }

    @Override
    public MatchController getMatchController() {
        return (MatchController) this.getController();
    }
}
