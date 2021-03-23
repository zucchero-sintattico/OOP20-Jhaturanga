package jhaturanga.views.replay;

import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.views.View;

public interface ReplayView extends View {

    /**
     * 
     * @return the history controller
     */
    default ReplayController getHistoryController() {
        return (ReplayController) this.getController();
    }
}
