package jhaturanga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

public class ModelImpl implements Model {

    private final List<User> users = new ArrayList<>();
    private final List<Match> matches = new ArrayList<>();

    @Override
    public final Optional<Match> getActualMatch() {
        if (!this.matches.isEmpty()) {
            return Optional.of(this.matches.get(this.matches.size() - 1));
        }
        return Optional.empty();
    }

    @Override
    public final Match createMatch(final GameType gameType, final Timer timer, final List<Player> players) {
        final Match match = new MatchImpl(gameType, Optional.ofNullable(timer), players);
        this.matches.add(match);
        return match;
    }

    @Override
    public final void addLoggedUser(final User user) {
        this.users.add(user);
    }

    @Override
    public final List<User> getLoggedUsers() {
        return Collections.unmodifiableList(this.users);
    }

}
