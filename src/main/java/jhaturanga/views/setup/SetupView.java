package jhaturanga.views.setup;

import jhaturanga.controllers.setup.SetupController;
import jhaturanga.views.JavaFXView;

/**
 * The view for Game Type Select Page.
 */
public interface SetupView extends JavaFXView {

    default SetupController getGameTypeController() {
        return (SetupController) this.getController();
    }
}
