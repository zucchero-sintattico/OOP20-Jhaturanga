package jhaturanga.views.game.javafx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.game.GameController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.board.BoardView;
import jhaturanga.views.game.GameView;

public final class GameViewImpl extends AbstractView implements GameView {

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        this.root.getChildren()
                .add(new BoardView(this.root.widthProperty(), this.root.heightProperty(), 8, 8));
    }

    @Override
    public void init() {

    }

    @Override
    public GameController getGameController() {
        return (GameController) this.getController();
    }

}
