package jhaturanga.model.replay;

import java.io.IOException;
import java.util.Set;

public interface SavedHistory {

    /**
     * Saving board.
     * 
     * @param boards to saving.
     * @throws IOException 
     */
    void save(Replay boards) throws IOException;

    /**
     * 
     * @param boardID
     * @return board witch have this ID
     */
    Replay getSavedBoard(String boardID);

    /**
     * 
     * @return all saved boards.
     */
    Set<Replay> getAllBoards();

}
