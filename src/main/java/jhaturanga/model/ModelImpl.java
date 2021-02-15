package jhaturanga.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public class ModelImpl implements Model {

    private final Set<Match> matches = new HashSet<>();

    @Override
    public final Optional<Match> getActualMatch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Match createMatch(GameType gameType, Timer timer, Collection<Player> players) {
        final Match match = new MatchImpl(gameType, Optional.ofNullable(timer), players);
        this.matches.add(match);
        return match;
    }

}
