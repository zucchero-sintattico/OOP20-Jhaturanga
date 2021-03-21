package jhaturanga.model.savedhistory;

import java.io.IOException;
import java.util.Set;

public interface SavedHistory {

    /**
     * Saving board.
     * 
     * @param boards to saving.
     * @throws IOException 
     */
    void save(BoardState boards) throws IOException;

    /**
     * 
     * @param boardID
     * @return board witch have this ID
     */
    BoardState getSavedBoard(String boardID);

    /**
     * 
     * @return all saved boards.
     */
    Set<BoardState> getAllBoards();

}
