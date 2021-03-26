package jhaturanga.model.match.builder;

import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.timer.Timer;

public class MatchBuilderImpl implements MatchBuilder {

    private GameType gameType;
    private Timer timer;
    private boolean built;

    @Override
    public final MatchBuilder gameType(final GameType gameType) {
        this.gameType = gameType;
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
        if (this.gameType == null || this.timer == null) {
            throw new IllegalStateException("Arguments wasn't setted");
        }
        this.built = true;
        return new MatchImpl(this.gameType, this.timer);
    }

}
