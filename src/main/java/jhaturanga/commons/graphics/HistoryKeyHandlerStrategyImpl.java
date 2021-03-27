package jhaturanga.commons.graphics;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;

public final class HistoryKeyHandlerStrategyImpl implements HistoryKeyHandlerStrategy {

    private static final KeyCode PREV_KEY_CODE = KeyCode.A;
    private static final KeyCode NEXT_KEY_CODE = KeyCode.D;

    private final GraphicalBoard board;
    private final HistoryNavigationController historyStrategy;

    public HistoryKeyHandlerStrategyImpl(final GraphicalBoard board,
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

        if (event.getCode().equals(PREV_KEY_CODE)) {
            this.handlePrev();
        } else if (event.getCode().equals(NEXT_KEY_CODE)) {
            this.handleNext();
        }
        event.consume();

        // TODO: REQUEST FOCUS
        // matchView.grid.requestFocus();
    }
}
