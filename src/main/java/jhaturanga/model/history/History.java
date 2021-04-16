package jhaturanga.model.history;

import java.util.List;

import jhaturanga.model.board.Board;

/**
 * The entity that manage the saving of the states of the game.
 *
 */
public interface History {

    /**
     * Add a movement to be saved in the history.
     */
    void updateHistory();

    /**
     * Use this method to upload a match history.
     * 
     * @param boardHistory - the List<Board> representing the History of the match
     *                     uploaded.
     */
    void updateWithNewHistory(List<Board> boardHistory);

    /**
     * 
     * @param index represents the index of move to which you want to know the move
     * @return Movement
     */
    Board getBoardAtIndex(int index);

    /**
     * Used this method to get the full history till current moment.
     * 
     * @return List<Board> representing all the boards of each turn/move
     */
    List<Board> getAllBoards();

}
