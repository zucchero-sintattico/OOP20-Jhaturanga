package jhaturanga.controllers.history;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;

public class HistoryControllerImpl extends AbstractController implements HistoryController {

    private int index;

    @Override
    public final Optional<Board> getPrevBoard() {
        if (index > 0) {
            index--;
            return Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public final Optional<Board> getNextBoard() {
        if (index < this.getModel().getActualMatch().get().getBoardFullHistory().size() - 1) {
            index++;
            return Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public final Board getFirstBoard() {
        return this.getModel().getActualMatch().get().getBoardFullHistory().get(0);
    }

}
