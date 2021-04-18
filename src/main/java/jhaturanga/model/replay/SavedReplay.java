package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

/**
 * The Interface SavedReplay.
 */
public interface SavedReplay {

    /**
     * Saving board.
     *
     * @param replayData to saving.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    void save(ReplayData replayData) throws IOException;

    /**
     * Gets the saved replay.
     *
     * @param matchID the match ID
     * @return board witch have this ID
     */
    ReplayData getSavedReplay(String matchID);

    /**
     * Gets the all boards.
     *
     * @return all saved boards.
     */
    Set<ReplayData> getAllBoards();

}
