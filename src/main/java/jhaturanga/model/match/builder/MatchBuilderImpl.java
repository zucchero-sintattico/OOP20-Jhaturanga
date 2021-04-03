package jhaturanga.model.match.builder;

import jhaturanga.model.game.Game;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.timer.Timer;

public class MatchBuilderImpl implements MatchBuilder {

    private Game game;
    private Timer timer;
    private boolean built;

    @Override
    public final MatchBuilder game(final Game gameType) {
        this.game = gameType;
        return this;
    }

    @Override
    public final MatchBuilder timer(final Timer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public final Match build() {
        if (this.built) {
            throw new IllegalStateException("Alredy Built");
        }
        if (this.game == null || this.timer == null) {
            throw new IllegalStateException("Arguments wasn't setted");
        }
        this.built = true;
        return new MatchImpl(this.game, this.timer);
    }

}
