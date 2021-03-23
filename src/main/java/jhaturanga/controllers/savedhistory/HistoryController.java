package jhaturanga.controllers.savedhistory;

import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.savedhistory.BoardState;

public interface HistoryController extends Controller {

    /**
     * 
     * @return ordered by data list of saved match
     */
    List<BoardState> getAllSavedMatchDataOrder();

    /**
     * 
     * @param boards which wont loading
     */
    void play(BoardState boards);
}
