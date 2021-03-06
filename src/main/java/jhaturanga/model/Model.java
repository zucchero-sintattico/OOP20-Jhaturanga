package jhaturanga.model;

import java.util.List;
import java.util.Optional;

import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

/**
 * The Model class of MVC pattern.
 */
public interface Model {

    // USERS FUNCTIONALITY

    /**
     * Set the first user.
     * 
     * @param user - the user to be setted
     */
    void setFirstUser(User user);

    /**
     * Set the second user.
     * 
     * @param user - the user to be setted
     */
    void setSecondUser(User user);

    /**
     * Get the first user.
     * 
     * @return an optional of user
     */
    Optional<User> getFirstUser();

    /**
     * Get the second user.
     * 
     * @return an optional of user
     */
    Optional<User> getSecondUser();

    // GAME FUNCTIONALITY

    /**
     * @return the actual matches if presents.
     */
    Optional<Match> getActualMatch();

    /**
     * Create a new Match.
     * 
     * @param gameType - the game Type
     * @param timer    - the timer
     * @param players  - the players
     * @return the match
     */
    Match createMatch(GameType gameType, Timer timer, List<Player> players);

}
