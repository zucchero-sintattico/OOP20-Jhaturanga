package jhaturanga.model.history;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.Movement;

public interface History {

    /**
     * Add a movement to be saved in the history.
     * 
     * @param movement is the move to add to the history
     */
    void addMoveToHistory(Movement movement);

    /**
     * 
     * @param index represents the index of move to which you want to know the move
     * @return Movement
     */
    Board getBoardAtIndex(int index);

}
