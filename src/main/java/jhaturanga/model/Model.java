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
     * Add a new logged user.
     * 
     * @param user - the user
     */
    void addLoggedUser(User user);

    /**
     * Remove logged user.
     * 
     * @param user - the user
     */
    void removeLoggedUser(User user);

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
     * @return the match
     */
    Match createMatch();

    /**
     * Sets the Timer for the match.
     * 
     * @param timer
     */
    void setTimer(Timer timer);

    /**
     * Gets the match's timer.
     * 
     * @return Optional<Timer>
     */
    Optional<Timer> getTimer();

    // TODO: TOMMASO DOCUMENTA!
    /**
     * 
     * @param gameType
     */
    void setGameType(GameType gameType);

    /**
     * 
     * @return Optional<GameType>
     */
    Optional<GameType> getGameType();

    /**
     * Sets the white player for the gametype.
     * 
     * @param player
     */
    void setWhitePlayer(Player player);

    /**
     * Sets the black player for the gametype.
     * 
     * @param player
     */
    void setBlackPlayer(Player player);

    /**
     * Gets the white player for the gametype.
     * 
     * @return player
     */
    Player getWhitePlayer();

    /**
     * Gets the black player for the gametype.
     * 
     * @return player
     */
    Player getBlackPlayer();

}
