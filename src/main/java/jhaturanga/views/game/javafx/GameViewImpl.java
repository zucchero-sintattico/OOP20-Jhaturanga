package jhaturanga.views.game.javafx;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.game.GameController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.board.BoardView;
import jhaturanga.views.game.GameView;

public final class GameViewImpl extends AbstractView implements GameView {

    private static final int MINIMUM_SCALE = 100;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        /*
         * this.root.getChildren() .add(new BoardView(this.root.widthProperty(),
         * this.root.heightProperty(), 8, 8));
         */
    }

    @Override
    public void init() {

        this.getStage().setMinHeight(MINIMUM_SCALE * this.getGameController().getBoardStatus().getColumns());
        this.getStage().setMinHeight(MINIMUM_SCALE * this.getGameController().getBoardStatus().getRows());

        System.out.println(this.getStage());
        final BoardView board = new BoardView(this.getGameController());

        this.getGameController().getBoardStatus().getBoardState().forEach(i -> board.addPiece(i));

        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()).divide(1));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()).divide(1));
        this.grid.add(board, 1, 1);
    }

    @Override
    public GameController getGameController() {
        return (GameController) this.getController();
    }

}
