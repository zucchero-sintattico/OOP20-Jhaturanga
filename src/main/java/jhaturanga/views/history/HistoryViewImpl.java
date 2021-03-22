package jhaturanga.views.history;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.controllers.history.HistoryController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class HistoryViewImpl extends AbstractView implements HistoryView {

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

//        final Pane board = new HistoryBoard(this.getHistoryController());
//        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
//        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
//        this.grid.setCenter(board);
//        this.player1Label.setText(this.getHistoryController().getModel().getWhitePlayer().getUser().getUsername());
//        this.player2Label.setText(this.getHistoryController().getModel().getBlackPlayer().getUser().getUsername());
    }

    @Override
    public HistoryController getHistoryController() {
        return (HistoryController) this.getController();
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        this.getHistoryController().getModel().clearMatchInfo();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

}
