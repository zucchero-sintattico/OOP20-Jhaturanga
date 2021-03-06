package jhaturanga.views.game.javafx;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jhaturanga.controllers.game.MatchController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.board.BoardView;
import jhaturanga.views.game.GameView;

public final class GameViewImpl extends AbstractView implements GameView {

    private static final int MINIMUM_SCALE = 100;

    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane grid;

    @FXML
    public void initialize() {

    }

    @Override
    public void init() {
        this.getStage().setMinWidth(MINIMUM_SCALE * this.getGameController().getBoardStatus().getColumns());
        this.getStage().setMinHeight(MINIMUM_SCALE * this.getGameController().getBoardStatus().getRows());

        final Node board = new BoardView(this.getGameController(),
                this.getGameController().getModel().getActualMatch().get().getGameController());

        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));

        this.grid.setCenter(board);
    }

    @Override
    public MatchController getGameController() {
        return (MatchController) this.getController();
    }

}
