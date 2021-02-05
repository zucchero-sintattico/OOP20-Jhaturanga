package jhaturanga.model.game;

import java.util.Optional;

import jhaturanga.model.player.Player;

public interface GameController {

    /**
     * Check if the game is over or not.
     * 
     * @return true if the game is completed, false otherwise
     */
    boolean isOver();
    
    /**
     * Check if the game is ended with a draw.
     * 
     * @return true if the game ended in a draw, false otherwise
     */
    boolean isDraw();

    /**
     * Get the winner of this game if exist.
     * 
     * @return the winner of this game, if present
     */
    Optional<Player> getWinner();
}
