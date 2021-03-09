package jhaturanga.views.game;

import jhaturanga.controllers.game.MatchController;
import jhaturanga.views.View;

/**
 * The view of the game page.
 */
public interface GameView extends View {

    /**
     * 
     * @return the game controller
     */
    MatchController getGameController();
}
