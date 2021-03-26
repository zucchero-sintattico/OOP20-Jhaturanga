package jhaturanga.controllers.replay;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

public interface ReplayController extends Controller {

    /**
     * Get the board state at the previous movement.
     * 
     * @return the board state
     */
    Board getFirstBoard();

    /**
     * Get the board state at the previous movement.
     * 
     * @return the board state
     */
    Optional<Board> getPrevBoard();

    /**
     * Get the board state at the next movement.
     * 
     * @return the board state
     */
    Optional<Board> getNextBoard();

    /**
     * Use this method to get the white player from the model.
     * 
     * @return the white user.
     */
    User getWhiteUser();

    /**
     * Use this method to get the black player from the model.
     * 
     * @return the black user.
     */
    User getBlackUser();

}
