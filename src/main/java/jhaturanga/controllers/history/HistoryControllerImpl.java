package jhaturanga.controllers.history;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;

public class HistoryControllerImpl extends AbstractController implements HistoryController {

    private static final int FIRST_BOARD_INDEX = 0;
    private int index;

    @Override
    public final Optional<Board> getPrevBoard() {
        return this.index > 0
                ? Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(--this.index))
                : Optional.empty();
    }

    @Override
    public final Optional<Board> getNextBoard() {
        return this.index < this.getModel().getActualMatch().get().getBoardFullHistory().size() - 1
                ? Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(++this.index))
                : Optional.empty();
    }

    @Override
    public final Board getFirstBoard() {
        return this.getModel().getActualMatch().get().getBoardFullHistory().get(FIRST_BOARD_INDEX);
    }

}
