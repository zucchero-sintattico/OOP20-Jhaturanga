package jhaturanga.model.movement;

import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.player.Player;

public interface MovementManager {

    /**
     * 
     * @param movement is the move to be executed on the Board
     * @return ActionType representing the type of action resulted from the action
     *         performed
     */
    MovementResult move(Movement movement);

    /**
     * Used to get the Player who's turn it is.
     * 
     * @return the Player who's turn it is
     */
    Player getPlayerTurn();

}
