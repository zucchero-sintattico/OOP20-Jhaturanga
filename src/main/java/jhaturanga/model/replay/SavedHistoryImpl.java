package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

import jhaturanga.commons.datastorage.HistoryDataStorageStrategy;

public final class SavedHistoryImpl implements SavedHistory {

    @Override
    public void save(final Replay boards) throws IOException {
        HistoryDataStorageStrategy.put(boards, boards.getMatchID());
    }

    @Override
    public Replay getSavedBoard(final String boardID) {
        return HistoryDataStorageStrategy.getBoard(boardID).get();
    }

    @Override
    public Set<Replay> getAllBoards() {
        return HistoryDataStorageStrategy.getAllBoard().get();
    }

}
