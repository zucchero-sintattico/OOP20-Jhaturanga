package jhaturanga.views.replay;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class ReplayViewImpl extends AbstractView implements ReplayView {

//    @FXML
//    private AnchorPane root;
//
//    @FXML
//    private BorderPane grid;
//
//    @FXML
//    private Label timerP1;
//
//    @FXML
//    private Label timerP2;
//
//    @FXML
//    private Label player1Label;
//
//    @FXML
//    private Label player2Label;

//    @FXML
//    public void initialize() {
//
//    }

    @Override
    public void init() {
        // TODO: IMPLEMENT
//        final Pane board = new HistoryBoard(this.getHistoryController());
//        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
//        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
//        this.grid.setCenter(board);
//
//        this.getHistoryController().getWhitePlayer().ifPresentOrElse(
//                player -> this.player1Label.setText(player.getUser().getUsername()),
//                () -> this.player1Label.setText("No User present"));
//
//        this.getHistoryController().getBlackPlayer().ifPresentOrElse(
//                player -> this.player2Label.setText(player.getUser().getUsername()),
//                () -> this.player2Label.setText("No User present"));

    }

    @Override
    public ReplayController getHistoryController() {
        return (ReplayController) this.getController();
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        this.getHistoryController().getApplicationInstance().clearMatchInfo();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

}
