package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public interface SavedReplay {

    /**
     * Saving board.
     * 
     * @param boards to saving.
     * @throws IOException 
     */
    void save(ReplayData boards) throws IOException;

    /**
     * 
     * @param boardID
     * @return board witch have this ID
     */
    ReplayData getSavedBoard(String boardID);

    /**
     * 
     * @return all saved boards.
     */
    Set<ReplayData> getAllBoards();

}
