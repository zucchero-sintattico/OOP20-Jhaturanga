package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public interface SavedReplay {

    /**
     * Saving board.
     * 
     * @param replayData to saving.
     * @throws IOException 
     */
    void save(ReplayData replayData) throws IOException;

    /**
     * 
     * @param matchID
     * @return board witch have this ID
     */
    ReplayData getSavedReplay(String matchID);

    /**
     * 
     * @return all saved boards.
     */
    Set<ReplayData> getAllBoards();

}
