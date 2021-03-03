package jhaturanga.views.game.javafx;

import javafx.beans.binding.Bindings;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
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
    private Text timerP1;

    @FXML
    private Text timerP2;

    @FXML
    public void initialize() {

    }

    @Override
    public void init() {
        this.getGameController().start();
        //this.getGameController().getModel().getTimer().get().start(this.getGameController().getModel().getWhitePlayer());
        this.getStage().setMinWidth(MINIMUM_SCALE * this.getGameController().getBoardStatus().getColumns());
        this.getStage().setMinHeight(MINIMUM_SCALE * this.getGameController().getBoardStatus().getRows());

        final Node board = new BoardView(this.getGameController());

        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));

        this.grid.setCenter(board);

        //this.getController().getModel().getTimer().get().start(this.getGameController().getModel().getWhitePlayer());

        Runnable runnable = () -> {
            while (true) {
                this.timerP1.setText(String.valueOf(this.getController().getModel().getTimer().get()
                        .getRemaningTime(this.getGameController().getModel().getWhitePlayer())));

                this.timerP2.setText(String.valueOf(this.getController().getModel().getTimer().get()
                        .getRemaningTime(this.getGameController().getModel().getBlackPlayer())));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };
        final Thread t = new Thread(runnable);
        t.start();
    }

    @Override
    public MatchController getGameController() {
        return (MatchController) this.getController();
    }

}
