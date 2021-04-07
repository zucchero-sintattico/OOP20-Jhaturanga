package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public final class SavedReplayImpl implements SavedReplay {

    @Override
    public void save(final ReplayData boards) throws IOException {
        ReplayDataStorageStrategy.put(boards, boards.getMatchID());
    }

    @Override
    public ReplayData getSavedBoard(final String boardID) {
        return ReplayDataStorageStrategy.getBoard(boardID).get();
    }

    @Override
    public Set<ReplayData> getAllBoards() {
        return ReplayDataStorageStrategy.getAllBoard().get();
    }

}
