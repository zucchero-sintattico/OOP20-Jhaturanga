package jhaturanga.instance;

import java.util.Optional;

import jhaturanga.model.match.Match;
import jhaturanga.model.user.User;

public final class ApplicationInstanceImpl implements ApplicationInstance {

    private User firstUser;
    private User secondUser;
    private Match match;

    @Override
    public void setFirstUser(final User user) {
        this.firstUser = user;
    }

    @Override
    public void setSecondUser(final User user) {
        this.secondUser = user;
    }

    public Optional<User> getFirstUser() {
        return Optional.ofNullable(this.firstUser);
    }

    @Override
    public Optional<User> getSecondUser() {
        return Optional.ofNullable(this.secondUser);
    }

    @Override
    public void setMatch(final Match match) {
        this.match = match;
    }

    @Override
    public Optional<Match> getMatch() {
        return Optional.ofNullable(this.match);
    }

    @Override
    public void deleteMatch() {
        this.match = null;
    }

}
