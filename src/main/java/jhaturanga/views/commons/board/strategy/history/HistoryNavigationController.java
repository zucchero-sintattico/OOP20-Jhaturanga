package jhaturanga.views.commons.board.strategy.history;

import java.util.Optional;

import jhaturanga.model.board.Board;

public interface HistoryNavigationController {

    Optional<Board> getPreviousBoard();

    Optional<Board> getNextBoard();
}
