package jhaturanga.model.history;

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
    Movement getMoveAtIndex(int index);

    /**
     * Used to get the previous move in the match compared to the one we are
     * viewing.
     * 
     * @return Movement representing the last movement in the match history
     */
    Movement getPreviousMove();

    /**
     * Used to get the next move in the match compared to the one we are viewing.
     * 
     * @return Movement representing the next movement in the match history
     */
    Movement getNextMove();
}
