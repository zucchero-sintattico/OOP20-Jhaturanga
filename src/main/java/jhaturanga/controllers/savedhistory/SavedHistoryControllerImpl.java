package jhaturanga.controllers.savedhistory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.savedhistory.BoardState;
import jhaturanga.model.savedhistory.SavedHistory;
import jhaturanga.model.savedhistory.SavedHistoryImpl;

public final class SavedHistoryControllerImpl extends AbstractController implements SavedHistoryController {

    private final SavedHistory savedMatch = new SavedHistoryImpl();

    @Override
    public List<BoardState> getAllSavedMatchDataOrder() {
        final List<BoardState> orderByDateMatch = new ArrayList<>(this.savedMatch.getAllBoards());
        orderByDateMatch.sort(Comparator.comparing(BoardState::getDate));
        return orderByDateMatch;
    }

    @Override
    public void play(final BoardState boards) {

    }

}
