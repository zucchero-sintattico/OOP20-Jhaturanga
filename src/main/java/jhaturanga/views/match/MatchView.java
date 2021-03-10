package jhaturanga.views.match;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.views.View;

/**
 * The view of the game page.
 */
public interface MatchView extends View {

    /**
     * 
     * @return the game controller
     */
    MatchController getGameController();
}
