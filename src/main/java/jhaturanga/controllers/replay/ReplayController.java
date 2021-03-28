package jhaturanga.controllers.replay;

import jhaturanga.commons.graphics.strategy.history.HistoryNavigationController;
import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

public interface ReplayController extends Controller, HistoryNavigationController {

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

}
