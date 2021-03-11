package jhaturanga.views.savedhistory;

import jhaturanga.controllers.savedhistory.SavedHistoryController;
import jhaturanga.views.View;

public interface HistoryView extends View {
    /**
     * 
     * @return element used to save the board history.
     */
    SavedHistoryController getSavedHistoryController();

}
