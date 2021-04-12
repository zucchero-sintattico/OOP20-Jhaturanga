package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public final class SavedReplayImpl implements SavedReplay {

    @Override
    public void save(final ReplayData boards) throws IOException {
        ReplayDataStorage.put(boards, boards.getMatchID());
    }

    @Override
    public ReplayData getSavedReplay(final String boardID) {
        return ReplayDataStorage.getBoard(boardID).get();
    }

    @Override
    public Set<ReplayData> getAllBoards() {
        return ReplayDataStorage.getAllBoard();
    }

}
