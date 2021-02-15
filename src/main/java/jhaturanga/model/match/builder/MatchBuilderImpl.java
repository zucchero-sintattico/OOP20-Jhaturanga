package jhaturanga.model.match.builder;

import java.util.Collection;
import java.util.Optional;

import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public class MatchBuilderImpl implements MatchBuilder {

    private GameType gameType;
    private Collection<Player> players;
    private Timer timer;

    @Override
    public final MatchBuilder gameType(final GameType gameType) {
        this.gameType = gameType;
        return this;
    }

    @Override
    public final MatchBuilder players(final Collection<Player> players) {
        this.players = players;
        return this;
    }

    @Override
    public final MatchBuilder timer(final Timer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public final Match build() {
        return new MatchImpl(this.gameType, Optional.ofNullable(this.timer), this.players);
    }

}
