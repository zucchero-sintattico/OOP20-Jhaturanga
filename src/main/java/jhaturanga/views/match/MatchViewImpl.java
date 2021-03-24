package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.graphics.EndGamePopup;
import jhaturanga.commons.graphics.MatchBoardView;
import jhaturanga.model.timer.ObservableTimer;
import jhaturanga.views.AbstractJavaFXView;

public final class MatchViewImpl extends AbstractJavaFXView implements MatchView {

    @FXML
    private Label whitePlayerUsernameLabel;

    @FXML
    private Label whitePlayerRemainingTimeLabel;

    @FXML
    private Label blackPlayerUsernameLabel;

    @FXML
    private Label blackPlayerRemainingTimeLabel;

    @FXML
    private StackPane boardContainer;

    @Override
    public void init() {

        this.getMatchController().start();

        this.whitePlayerUsernameLabel.setText(this.getMatchController().getWhitePlayer().getUser().getUsername());
        this.blackPlayerUsernameLabel.setText(this.getMatchController().getBlackPlayer().getUser().getUsername());

        this.whitePlayerRemainingTimeLabel.setText(this.getMatchController().getWhiteReminingTime());
        this.blackPlayerRemainingTimeLabel.setText(this.getMatchController().getBlackReminingTime());

        final MatchBoardView board = new MatchBoardView(this, this::onMatchEnd);

        board.maxWidthProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));
        board.maxHeightProperty()
                .bind(Bindings.min(this.boardContainer.widthProperty(), this.boardContainer.heightProperty()));

        this.boardContainer.getChildren().add(board);

        new ObservableTimer(this.getMatchController().getTimer(), this::onTimeFinish, this::onTimeChange).start();

    }

    private void updateTimerLabels() {
        this.whitePlayerRemainingTimeLabel.setText(this.getMatchController().getWhiteReminingTime());
        this.blackPlayerRemainingTimeLabel.setText(this.getMatchController().getBlackReminingTime());
    }

    private void openEndGamePopup() {
        final EndGamePopup popup = new EndGamePopup();
        popup.setMessage("Game ended for " + this.getMatchController().matchStatus().toString());
        popup.setButtonAction(() -> {
            this.getMatchController().deleteMatch();
            // PageLoader.switchPage(this.getStage(), Pages.HOME,
            // this.getController().getApplicationInstance());
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
            final EndGamePopup popup = new EndGamePopup();
            popup.setMessage(
                    this.getMatchController().getPlayerTurn().getUser().getUsername() + " are you sure to give up?");
            popup.setButtonAction(() -> {
                this.onMatchEnd();
                popup.close();
            });
            popup.show();
        });
    }

}
