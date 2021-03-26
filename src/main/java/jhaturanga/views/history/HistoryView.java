package jhaturanga.views.history;

import jhaturanga.controllers.history.HistoryController;
import jhaturanga.views.JavaFXView;

public interface HistoryView extends JavaFXView {

    /**
     * 
     * @return element used to save the board history.
     */
    default HistoryController getHistoryController() {
        return (HistoryController) this.getController();
    }

}
