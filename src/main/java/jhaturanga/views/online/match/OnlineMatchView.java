package jhaturanga.views.online.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.graphics.EndGamePopup;
import jhaturanga.commons.graphics.MatchBoardView;
import jhaturanga.controllers.match.OnlineMatchController;
import jhaturanga.model.timer.TimerThread;
import jhaturanga.views.AbstractJavaFXView;

public final class OnlineMatchView extends AbstractJavaFXView {

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

    private MatchBoardView board;

    @Override
    public void init() {

        this.getStage().setOnCloseRequest(null);
        this.getOnlineMatchController().start();

        final MatchBoardView board = new MatchBoardView(this, this::onMatchEnd,
                this.getOnlineMatchController().isWhitePlayer());

        this.getOnlineMatchController().setOnMovementHandler((movementResult) -> {
            System.out.println("ON MOVEMENT HANDLER - CALL THE REDRAW");
            Platform.runLater(() -> {
                board.makeMovement(movementResult);
                // board.redraw(this.getOnlineMatchController().getBoardStatus());
            });

        });

        this.whitePlayerUsernameLabel.setText(this.getOnlineMatchController().getWhitePlayer().getUser().getUsername());
        this.blackPlayerUsernameLabel.setText(this.getOnlineMatchController().getBlackPlayer().getUser().getUsername());

        this.board = new MatchBoardView(this, this::onMatchEnd);

        this.board.maxWidthProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));
        this.board.maxHeightProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));

        this.topBar.prefWidthProperty().bind(this.board.widthProperty());
        this.topBar.maxWidthProperty().bind(this.board.widthProperty());
        this.bottomBar.prefWidthProperty().bind(this.board.widthProperty());
        this.bottomBar.maxWidthProperty().bind(this.board.widthProperty());

        this.boardContainer.getChildren().add(this.board);

        new TimerThread(this.getOnlineMatchController().getTimer(), this::onTimeFinish, this::onTimeChange).start();

        this.updateTimerLabels();

        this.getStage().setOnCloseRequest(e -> this.checkIfTimerIsPresentAndStopIt());
    }

    private void checkIfTimerIsPresentAndStopIt() {
        if (this.getOnlineMatchController().isMatchPresent()) {
            this.getOnlineMatchController().getTimer().stop();
        }
    }

    /**
     * Get the board for testing purpose.
     * 
     * @return the board view
     */
    public MatchBoardView getBoardView() {
        return this.board;
    }

    private void updateTimerLabels() {
        this.whitePlayerRemainingTimeLabel
                .setText(this.secondsToHumanReadableTime(this.getOnlineMatchController().getWhiteReminingTime()));
        this.blackPlayerRemainingTimeLabel
                .setText(this.secondsToHumanReadableTime(this.getOnlineMatchController().getBlackReminingTime()));
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
        popup.setMessage("Game ended for " + this.getOnlineMatchController().matchStatus().toString());
        popup.setButtonAction(() -> {
            this.getOnlineMatchController().deleteMatch();

            popup.close();
        });
        popup.show();
    }

    private void onMatchEnd() {
        try {
            this.getOnlineMatchController().saveMatch();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.getOnlineMatchController().getTimer().stop();
        this.openEndGamePopup();
    }

    private void onTimeChange() {
        Platform.runLater(this::updateTimerLabels);
    }

    private void onTimeFinish() {
        Platform.runLater(this::openEndGamePopup);
    }

    public OnlineMatchController getOnlineMatchController() {
        return (OnlineMatchController) this.getController();
    }

}
