package jhaturanga.commons.datastorage;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.savedhistory.BoardState;

public interface HistoryDataStorageStrategy {

    /**
     * save match.
     * 
     * @param id    of the match
     * @param match
     */
    void put(BoardState match, String id);

    /**
     * 
     * @return set containing all match.
     */
    Set<BoardState> getAllBoard();

    /**
     * 
     * @param id of required match
     * @return list containing all moves of match
     */
    Optional<BoardState> getBoard(String id);

}
