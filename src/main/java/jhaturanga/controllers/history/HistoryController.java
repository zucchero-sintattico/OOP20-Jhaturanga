package jhaturanga.controllers.history;

import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.replay.ReplayData;

public interface HistoryController extends Controller {

    /**
     * 
     * @return ordered by data list of saved match
     */
    List<ReplayData> getAllSavedReplaysOrdered();

    /**
     * 
     * @param boards which wont loading
     */
    void setReplay(ReplayData boards);
}
