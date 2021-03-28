package jhaturanga.commons.graphics.strategy.history;

import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jhaturanga.commons.graphics.board.GraphicalBoard;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;

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

    private void handlePrev() {
        // this.view.resetHighlightedTiles();
        this.historyStrategy.getPreviousBoard().ifPresent(board -> {
            this.board.redraw(board);
            Sound.play(SoundsEnum.MOVE);
        });
    }

    private void handleNext() {
        // this.view.resetHighlightedTiles();
        this.historyStrategy.getNextBoard().ifPresent(board -> {
            this.board.redraw(board);
            Sound.play(SoundsEnum.MOVE);
        });
    }

    @Override
    public void handle(final KeyEvent event) {

        if (PREV_KEY_CODES.contains(event.getCode())) {
            this.handlePrev();
        } else if (NEXT_KEY_CODES.contains(event.getCode())) {
            this.handleNext();
        }
        event.consume();

        // TODO: REQUEST FOCUS
        // matchView.grid.requestFocus();
    }
}
