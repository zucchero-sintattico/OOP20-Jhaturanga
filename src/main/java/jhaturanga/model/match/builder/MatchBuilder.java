package jhaturanga.model.match.builder;

import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.timer.Timer;

/**
 * A Builder for a Match.
 */
public interface MatchBuilder {

    /**
     * Set the type of game of this match.
     * 
     * @param gameType - the type of game of this match
     * @return this
     */
    MatchBuilder gameType(GameType gameType);

    /**
     * Set the timer for this game.
     * 
     * @param timer - the timer for this match
     * @return this
     */
    MatchBuilder timer(Timer timer);

    /**
     * Get the created match.
     * 
     * @return the created match.
     */
    Match build();

}
