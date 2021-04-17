package jhaturanga.controllers.replay;

import java.util.Optional;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

/**
 * Basic implementation of the ReplayController.
 *
 */
public final class ReplayControllerImpl extends BasicController implements ReplayController {

    private static final int FIRST_BOARD_INDEX = 0;
    private int index;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getPreviousBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getReplay().get().getBoards().get(--this.index))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getNextBoard() {
        return this.index < this.getApplicationInstance().getReplay().get().getBoards().size() - 1
                ? Optional.of(this.getApplicationInstance().getReplay().get().getBoards().get(++this.index))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getFirstBoard() {
        return this.getApplicationInstance().getReplay().get().getBoards().get(FIRST_BOARD_INDEX);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getWhiteUser() {
        return this.getApplicationInstance().getReplay().get().getWhiteUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getBlackUser() {
        return this.getApplicationInstance().getReplay().get().getBlackUser();
    }

}
