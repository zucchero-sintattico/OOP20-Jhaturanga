package jhaturanga.views.game;

import jhaturanga.controllers.game.GameController;
import jhaturanga.views.AbstractView;

public class GameViewImpl extends AbstractView implements GameView {

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public GameController getGameController() {
        return (GameController) this.getController();
    }

}
