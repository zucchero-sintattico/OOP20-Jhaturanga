package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public final class SavedHistoryImpl implements SavedHistory {

    @Override
    public void save(final Replay boards) throws IOException {
        ReplayDataStorageStrategy.put(boards, boards.getMatchID());
    }

    @Override
    public Replay getSavedBoard(final String boardID) {
        return ReplayDataStorageStrategy.getBoard(boardID).get();
    }

    @Override
    public Set<Replay> getAllBoards() {
        return ReplayDataStorageStrategy.getAllBoard().get();
    }

}
