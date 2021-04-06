package jhaturanga.views.replay;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.graphics.board.ReplayBoard;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class ReplayView extends AbstractJavaFXView {

    @FXML
    private StackPane container;

    @Override
    public void init() {
        final ReplayBoard board = new ReplayBoard(this.getReplayController());
        this.container.getChildren().add(board);
        board.maxWidthProperty().bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));
        board.maxHeightProperty().bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        this.getReplayController().getApplicationInstance().deleteReplay();
        PageLoader.switchPage(this.getStage(), Pages.HISTORY, this.getController().getApplicationInstance());
    }

    private ReplayController getReplayController() {
        return (ReplayController) this.getController();
    }
}
