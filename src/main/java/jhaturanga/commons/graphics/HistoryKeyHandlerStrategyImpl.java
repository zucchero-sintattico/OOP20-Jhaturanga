package jhaturanga.commons.graphics;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;

public final class HistoryKeyHandlerStrategyImpl implements HistoryKeyHandlerStrategy {

    private static final KeyCode PREV_KEY_CODE = KeyCode.A;
    private static final KeyCode NEXT_KEY_CODE = KeyCode.D;

    private final MatchBoardView view;
    private final MatchController controller;

    public HistoryKeyHandlerStrategyImpl(final MatchBoardView view, final MatchController controller) {
        this.view = view;
        this.controller = controller;
    }

    private void handlePrev() {
        this.view.resetHighlightedTiles();
        this.controller.getPrevBoard().ifPresent(board -> {
            this.view.redraw(board);
            Sound.play(SoundsEnum.MOVE);
        });
    }

    private void handleNext() {
        this.view.resetHighlightedTiles();
        this.controller.getNextBoard().ifPresent(board -> {
            this.view.redraw(board);
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
