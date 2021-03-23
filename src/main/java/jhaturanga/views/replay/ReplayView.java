package jhaturanga.views.replay;

import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.views.JavaFXView;

public interface ReplayView extends JavaFXView {

    /**
     * 
     * @return the history controller
     */
    default ReplayController getHistoryController() {
        return (ReplayController) this.getController();
    }
}
