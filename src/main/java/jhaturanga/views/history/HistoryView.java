package jhaturanga.views.history;

import jhaturanga.controllers.savedhistory.HistoryController;
import jhaturanga.views.View;

public interface HistoryView extends View {

    /**
     * 
     * @return element used to save the board history.
     */
    HistoryController getSavedHistoryController();

}
