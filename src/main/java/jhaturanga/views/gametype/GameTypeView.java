package jhaturanga.views.gametype;

import jhaturanga.controllers.gametype.GameTypeController;
import jhaturanga.views.View;

/**
 * The view for Game Type Select Page.
 */
public interface GameTypeView extends View {

    default GameTypeController getGameTypeController() {
        return (GameTypeController) this.getController();
    }
}
