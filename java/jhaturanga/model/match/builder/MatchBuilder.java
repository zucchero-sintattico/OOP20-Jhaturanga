package jhaturanga.model.match.builder;

import java.util.Collection;

import javax.management.timer.Timer;

import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;

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
     * Set the players for the match.
     * 
     * @param players
     * @return this
     */
    MatchBuilder players(Collection<Player> players);

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
