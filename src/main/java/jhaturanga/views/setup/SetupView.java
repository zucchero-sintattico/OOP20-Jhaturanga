package jhaturanga.views.setup;

import jhaturanga.controllers.gametype.GameTypeController;
import jhaturanga.views.View;

/**
 * The view for Game Type Select Page.
 */
public interface SetupView extends View {

    default GameTypeController getGameTypeController() {
        return (GameTypeController) this.getController();
    }
}
