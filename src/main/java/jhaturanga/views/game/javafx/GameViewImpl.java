package jhaturanga.views.game.javafx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jhaturanga.controllers.game.GameController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.board.BoardView;
import jhaturanga.views.game.GameView;

public final class GameViewImpl extends AbstractView implements GameView {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public void initialize() {
        this.mainAnchorPane.getChildren()
                .add(new BoardView(this.mainAnchorPane.widthProperty(), this.mainAnchorPane.heightProperty(), 8, 8));
    }

    @Override
    public void init() {

    }

    @Override
    public GameController getGameController() {
        return (GameController) this.getController();
    }

}
