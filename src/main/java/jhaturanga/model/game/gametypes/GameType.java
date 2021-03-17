package jhaturanga.model.game.gametypes;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.MovementManager;

/**
 * A generic type of game.
 */
public interface GameType {

    /**
     * Get the name of this game type.
     * 
     * @return the game's name.
     */
    String getGameName();

    /**
     * Get the game controller for this game type.
     * 
     * @return the game controller.
     */
    GameController getGameController();

    /**
     * Get the starting board of this game type.
     * 
     * @return the starting board.
     */
    Board getStartingBoard();

    /**
     * Get the GameType's specific MovementManager.
     * 
     * @return MovementManager is the GameType's specific movementManager
     */
    MovementManager getMovementManager();

}
