package jhaturanga.views.match.online;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jhaturanga.commons.graphics.MatchBoardView;
import jhaturanga.controllers.match.OnlineMatchController;
import jhaturanga.model.timer.TimerThread;
import jhaturanga.views.AbstractJavaFXView;

public final class OnlineMatchViewImpl extends AbstractJavaFXView implements OnlineMatchView {

    private static final int MINIMUM_SCALE = 100;

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

    }

    private void onTimeFinish() {

    }

    @Override
    public void init() {

        this.getOnlineMatchController().start();

        this.getStage().setMinWidth(MINIMUM_SCALE * this.getOnlineMatchController().getBoardStatus().getColumns());
        this.getStage().setMinHeight(MINIMUM_SCALE * this.getOnlineMatchController().getBoardStatus().getRows());

        // TODO
        final MatchBoardView board = new MatchBoardView(this, () -> System.out.println(),
                this.getOnlineMatchController().isWhitePlayer());

        // TODO: AGGIUNGERE HANDLE DELLA MOVEMENT
        this.getOnlineMatchController().setOnMovementHandler((movementResult) -> {
            System.out.println("ON MOVEMENT HANDLER - CALL THE REDRAW");
            Platform.runLater(() -> {
                // board.makeMovement(movementResult);
                // board.redraw(this.getOnlineMatchController().getBoardStatus());
            });

        });

        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));

        this.grid.setCenter(board);

        new TimerThread(this.getOnlineMatchController().getTimer(), this::onTimeFinish, this::onTimeChange).start();

        this.player1Label.setText(this.getOnlineMatchController().getWhitePlayer().getUser().getUsername());
        this.player2Label.setText(this.getOnlineMatchController().getBlackPlayer().getUser().getUsername());

    }

    @Override
    public OnlineMatchController getOnlineMatchController() {
        return (OnlineMatchController) this.getController();
    }

    @FXML
    public void giveUpMatch(final Event event) throws IOException {
    }

    @FXML
    public void backToMenu(final Event event) throws IOException {

    }

    @FXML
    public void saveMatch(final Event event) throws IOException {

    }

}
