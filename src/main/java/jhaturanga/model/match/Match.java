package jhaturanga.model.match;

import java.util.Optional;

import jhaturanga.model.piecemanagament.Player;

/**
 *
 */
public interface Match {

    /**
     * Get the actual game ID.
     * 
     * @return the ID of this match.
     */
    int getMatchID();

    /**
     * Start the actual game.
     */
    void start();

    /**
     * Get if the game is completed or not.
     * 
     * @return true if the game is completed, false otherwise.
     */
    boolean isCompleted();

    /**
     * Get the winner of this game but only if present.
     * @return the winner of this game, if present.
     */
    Optional<Player> winner();
}
