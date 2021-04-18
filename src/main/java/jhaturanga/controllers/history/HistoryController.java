package jhaturanga.controllers.history;

import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.replay.ReplayData;

/**
 * The controller for the history page view.
 *
 */
public interface HistoryController extends Controller {

    /**
     * Get all the saved match replays.
     * 
     * @return list of saved match ordered by data
     */
    List<ReplayData> getAllSavedReplaysOrdered();

    /**
     * Set the replay to view.
     * 
     * @param replay - the replay to be set
     */
    void setReplay(ReplayData replay);
}
