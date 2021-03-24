package jhaturanga.controllers.history;

import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.replay.Replay;

public interface HistoryController extends Controller {

    /**
     * 
     * @return ordered by data list of saved match
     */
    List<Replay> getAllSavedMatchDataOrder();

    /**
     * 
     * @param boards which wont loading
     */
    void play(Replay boards);
}
