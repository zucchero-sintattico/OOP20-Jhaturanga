package jhaturanga.views.commons.board.strategy.history;

import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jhaturanga.commons.settings.media.sound.Sound;
import jhaturanga.commons.settings.media.sound.SoundsEnum;
import jhaturanga.controllers.match.HistoryNavigationController;
import jhaturanga.model.board.Board;
import jhaturanga.views.commons.board.GraphicalBoard;
import jhaturanga.views.commons.board.MatchBoard;

public final class NormalHistoryKeyHandlerStrategy implements HistoryKeyHandlerStrategy {

    private static final List<KeyCode> PREV_KEY_CODES = List.of(KeyCode.A, KeyCode.LEFT);
    private static final List<KeyCode> NEXT_KEY_CODES = List.of(KeyCode.D, KeyCode.RIGHT);

    private final GraphicalBoard board;
    private final HistoryNavigationController historyStrategy;

    public NormalHistoryKeyHandlerStrategy(final GraphicalBoard board,
            final HistoryNavigationController historyStrategy) {
        this.board = board;
        this.historyStrategy = historyStrategy;
    }

    private void redraw(final Board board) {
        if (this.board instanceof MatchBoard) {
            final MatchBoard matchBoard = (MatchBoard) this.board;
            matchBoard.resetHighlightedMovements();
            matchBoard.resetHightlightedPositions();
        }
        this.board.redraw(board);
        Sound.play(SoundsEnum.MOVE);
    }

    private void handlePrev() {
        this.historyStrategy.getPreviousBoard().ifPresent(this::redraw);
    }

    private void handleNext() {
        this.historyStrategy.getNextBoard().ifPresent(this::redraw);
    }

    @Override
    public void handle(final KeyEvent event) {
        if (PREV_KEY_CODES.contains(event.getCode())) {
            this.handlePrev();
        } else if (NEXT_KEY_CODES.contains(event.getCode())) {
            this.handleNext();
        }
        event.consume();
        board.getGrid().requestFocus();
    }
}
