package jhaturanga.controllers.history;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;

public interface HistoryController extends Controller {

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
}
