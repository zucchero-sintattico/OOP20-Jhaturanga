package jhaturanga.views.game;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.game.GameController;
import jhaturanga.views.CommandLineView;

public class CommandLineGameView implements GameView, CommandLineView {

    private GameController controller;
    private Stage stage;

    @Override
    public final Controller getController() {
        return this.controller;
    }

    @Override
    public final void setController(final Controller controller) {
        this.controller = (GameController) controller;
    }

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
