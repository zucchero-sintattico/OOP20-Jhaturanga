package jhaturanga.model;

import java.util.Optional;

import jhaturanga.model.match.Match;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.model.user.User;

/**
 * Implementation of the Model interface.
 */
public final class ApplicationInstance implements Model {

    private User firstUser;
    private User secondUser;
    private Match match;
    private ReplayData replay;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFirstUser(final User user) {
        this.firstUser = user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecondUser(final User user) {
        this.secondUser = user;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<User> getFirstUser() {
        return Optional.ofNullable(this.firstUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getSecondUser() {
        return Optional.ofNullable(this.secondUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMatch(final Match match) {
        this.match = match;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Match> getMatch() {
        return Optional.ofNullable(this.match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMatch() {
        this.match = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReplay(final ReplayData replay) {
        this.replay = replay;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ReplayData> getReplay() {
        return Optional.ofNullable(this.replay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReplay() {
        this.replay = null;

    }

}
