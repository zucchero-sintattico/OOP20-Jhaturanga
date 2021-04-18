package jhaturanga.views.commons.board.strategy.history;

import java.util.Optional;

import jhaturanga.model.board.Board;

/**
 * A simple controller for the navigation.
 */
public interface HistoryNavigationController {

    /**
     * @return the previous board, if present.
     */
    Optional<Board> getPreviousBoard();

    /**
     * @return the next board, if present.
     */
    Optional<Board> getNextBoard();
}
