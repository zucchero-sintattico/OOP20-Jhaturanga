package jhaturanga.views.replay;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class ReplayViewImpl extends AbstractJavaFXView implements ReplayView {

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

    @FXML
    private StackPane container;

    @Override
    public void init() {

        final Pane board = new ReplayBoard(this.getReplayController());
        this.container.getChildren().add(board);
        board.maxWidthProperty().bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));
        board.maxHeightProperty().bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));
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

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        this.getReplayController().getApplicationInstance().deleteReplay();
        PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getApplicationInstance());
    }

}
