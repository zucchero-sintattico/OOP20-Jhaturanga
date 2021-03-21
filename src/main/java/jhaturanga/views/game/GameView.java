package jhaturanga.views.game;

import jhaturanga.controllers.oldhome.OldHomeController;
import jhaturanga.views.View;

/**
 * The view of the home page.
 */
public interface GameView extends View {

    /**
     * Get the home controller instance.
     * 
     * @return the home controller
     */
    OldHomeController getHomeController();

}
