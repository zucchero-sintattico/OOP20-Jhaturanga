package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;

import jhaturanga.commons.Pair;
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
     * Use this method to get the board state at a wanted index.
     * 
     * @param index of which to get the movement
     * @return Board representing the wanted board state at the passed index
     */
    Board getBoardAtIndexFromHistory(int index);

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

    /**
     * Used to get the Player time remaining who's turn it is .
     * 
     * @return the Player time remaining who's turn it is
     */
    Pair<Player, Integer> getPlayerTimeRemaining();

    /**
     * 
     * @return list contain all board history
     */
    List<Board> getBoardFullHistory();
}
