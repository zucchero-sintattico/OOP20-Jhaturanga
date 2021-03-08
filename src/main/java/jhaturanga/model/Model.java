package jhaturanga.model;

import java.io.Serializable;
import java.util.Optional;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

/**
 * The Model class of MVC pattern.
 */
public interface Model extends Serializable {

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
     */
    void createMatch();

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
    void setGameType(GameTypesEnum gameType);

    /**
     * 
     * @return Optional<GameType>
     */
    Optional<GameTypesEnum> getGameType();

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
