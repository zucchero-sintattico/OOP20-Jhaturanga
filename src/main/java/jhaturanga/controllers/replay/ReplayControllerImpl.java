package jhaturanga.controllers.replay;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

public class ReplayControllerImpl extends AbstractController implements ReplayController {

    private static final int FIRST_BOARD_INDEX = 0;
    private int index;

    @Override
    public final Optional<Board> getPreviousBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getReplay().get().getBoards().get(--this.index))
                : Optional.empty();
    }

    @Override
    public final Optional<Board> getNextBoard() {
        return this.index < this.getApplicationInstance().getReplay().get().getBoards().size() - 1
                ? Optional.of(this.getApplicationInstance().getReplay().get().getBoards().get(++this.index))
                : Optional.empty();
    }

    @Override
    public final Board getFirstBoard() {
        return this.getApplicationInstance().getReplay().get().getBoards().get(FIRST_BOARD_INDEX);

    }

    @Override
    public final User getWhiteUser() {
        return this.getApplicationInstance().getReplay().get().getWhiteUser();
    }

    @Override
    public final User getBlackUser() {
        return this.getApplicationInstance().getReplay().get().getBlackUser();
    }

}
