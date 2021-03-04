package jhaturanga.controllers.game;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;

/**
 * The controller for the game page.
 */
public interface MatchController extends Controller {

    /**
     * Move a piece.
     * 
     * @param origin      - the origin's position
     * @param destination - the destination's position
     * @return true if the movement was made, false otherwise
     */
    boolean move(BoardPosition origin, BoardPosition destination);

    /**
     * Get the actual board status.
     * 
     * @return the status of the most recent board
     */
    Board getBoardStatus();

    /**
     * Get the board state at the previous movement.
     * 
     * @return the board state
     */
    Board getPrevBoard();

    /**
     * Get the board state at the next movement.
     * 
     * @return the board state
     */
    Board getNextBoard();

    /**
     * Check if the current game is not sync with the last move, in this case we are
     * navigation through the movement and we don't have to make any movement from
     * the GUI.
     * 
     * @return true if we are in navigation mode, false otherwise
     */
    boolean isInNavigationMode();

    /**
     * white remain time in minutes.
     * 
     * @return white remain time in minutes
     */
    String getWhiteReminingTime();

    /**
     * white remain time in minutes.
     * 
     * @return white remain time in minutes
     */
    String getBlackReminingTime();

    /**
     * start match.
     */
    void start();
    
    /**
     * get the status of the match.
     * @return true if the match is over
     */
    boolean isOver();
}
