package jhaturanga.views.match;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.timer.ObservableTimer;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class MatchViewImpl extends AbstractView implements MatchView {

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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timerP1.setText(getGameController().getWhiteReminingTime());
                timerP2.setText(getGameController().getBlackReminingTime());
            }
        });

    }

    private void onTimeFinish() {

    }

    @Override
    public void init() {
        this.getGameController().start();

        this.getStage().setMinWidth(MINIMUM_SCALE * this.getGameController().getBoardStatus().getColumns());
        this.getStage().setMinHeight(MINIMUM_SCALE * this.getGameController().getBoardStatus().getRows());

        final Node board = new BoardView(this.getGameController());

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
    public void giveUpMatch(final Event event) throws IOException {
        this.getGameController().getModel().getTimer().get().stop();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @FXML
    public void backToMenu(final Event event) throws IOException {
        this.getGameController().getModel().getTimer().get().stop();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @FXML
    public void saveMatch(final Event event) throws IOException {
        this.getGameController().saveMatch();
    }

}
