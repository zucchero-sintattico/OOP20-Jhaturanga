package jhaturanga.model.match;

import java.util.Optional;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;

/**
 *
 */
public interface Match {

    /**
     * Get the actual game ID.
     * 
     * @return the ID of this match.
     */
    String getMatchID();

    /**
     * Start the actual game.
     */
    void start();

    /**
     * Try to make a movement.
     * 
     * @param movement - the movement to make
     * @return true if the movement was made, false otherwise
     */
    boolean move(Movement movement);

    /**
     * Get if the game is completed or not.
     * 
     * @return true if the game is completed, false otherwise.
     */
    boolean isCompleted();

    /**
     * Get the winner of this game but only if present.
     * 
     * @return the winner of this game, if present.
     */
    Optional<Player> winner();

    /**
     * Use this method to get a move at a wanted index.
     * 
     * @param index of which to get the movement
     * @return Movement representing the wanted movement at the passed index
     */
    Movement getMoveAtIndexFromHistory(int index);

    /**
     * Used to go to the previous move in the match compared to the one we are
     * viewing.
     */
    void goToPreviousMove();

    /**
     * Used to got to the next move in the match compared to the one we are viewing.
     * 
     */
    void goToNextMove();

    /**
     * Use this method to get the actual Board state.
     * 
     * @return Board representing the the state of the board
     */
    Board getBoard();

    /**
     * Get the game controller of this match.
     * 
     * @return the game controller
     */
    GameController getGameController();

}
