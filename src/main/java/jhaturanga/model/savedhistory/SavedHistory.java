package jhaturanga.model.savedhistory;

import java.util.Set;

public interface SavedHistory {

    /**
     * Saving board.
     * 
     * @param boards to saving.
     */
    void save(BoardState boards);

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
