package jhaturanga.views.game;

import jhaturanga.controllers.game.GameController;
import jhaturanga.views.View;

/**
 * The view of the game page.
 */
public interface GameView extends View {

    /**
     * 
     * @return the game controller
     */
    GameController getGameController();
}
