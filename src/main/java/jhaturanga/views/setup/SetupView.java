package jhaturanga.views.setup;

import jhaturanga.controllers.setup.SetupController;
import jhaturanga.views.View;

/**
 * The view for Game Type Select Page.
 */
public interface SetupView extends View {

    default SetupController getGameTypeController() {
        return (SetupController) this.getController();
    }
}
