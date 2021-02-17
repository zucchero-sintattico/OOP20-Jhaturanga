package jhaturanga.model;

import java.util.List;
import java.util.Optional;

import jhaturanga.model.game.GameType;
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
     * Add a new logged user.
     * 
     * @param user - the user
     */
    void addLoggedUser(User user);

    /**
     * Get the actual logged users.
     * 
     * @return the logged users
     */
    List<User> getLoggedUsers();

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
