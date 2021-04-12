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

    /**
     * Get the previous board of the history navigation.
     * 
     * @return the previous board if present, an Optional.empty otherwise
     */
    Optional<Board> getPreviousBoard();

    /**
     * Get the next board of the history navigation.
     * 
     * @return the next board if present, an Optional.empty otherwise
     */
    Optional<Board> getNextBoard();

}
