package jhaturanga.views.game;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jhaturanga.controllers.game.GameController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.board.BoardView;

public final class GameViewImpl extends AbstractView implements GameView {

    @FXML
    private AnchorPane mainAnchorPane;

    @Override
    public void init() {
        this.mainAnchorPane.getChildren()
                .add(new BoardView(this.mainAnchorPane.widthProperty(), this.mainAnchorPane.heightProperty(),
                        this.getGameController().getBoardStatus().getRows(),
                        this.getGameController().getBoardStatus().getColumns()));
    }

    @Override
    public GameController getGameController() {
        return (GameController) this.getController();
    }

}
