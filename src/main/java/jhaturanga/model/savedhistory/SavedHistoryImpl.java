package jhaturanga.model.savedhistory;

import java.util.Set;

import jhaturanga.commons.datastorage.HistoryDataStorageStrategy;

public final class SavedHistoryImpl implements SavedHistory {

    @Override
    public void save(final BoardState boards) {
        HistoryDataStorageStrategy.put(boards, boards.getMatchID());
    }

    @Override
    public BoardState getSavedBoard(final String boardID) {
        return HistoryDataStorageStrategy.getBoard(boardID).get();
    }

    @Override
    public Set<BoardState> getAllBoards() {
        return HistoryDataStorageStrategy.getAllBoard().get();
    }

}
