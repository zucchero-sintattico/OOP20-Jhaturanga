package jhaturanga.views.match;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.views.JavaFXView;

/**
 * The view of the game page.
 */
public interface MatchView extends JavaFXView {

    /**
     * 
     * @return the game controller
     */
    MatchController getGameController();
}
