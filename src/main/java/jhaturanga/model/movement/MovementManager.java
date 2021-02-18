package jhaturanga.model.movement;

import jhaturanga.model.player.Player;

public interface MovementManager {

    /**
     * 
     * @param movement is the move to be executed on the Board
     * @return a boolean that tells if it was possible to execute the movement
     */
    boolean move(Movement movement);

    /**
     * Used to get the Player who's turn it is.
     * 
     * @return the Player who's turn it is
     */
    Player getPlayerTurn();

}
