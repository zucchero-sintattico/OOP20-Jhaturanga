package jhaturanga.model.user;

import java.util.Optional;

/**
 * User interface represent an application user
 * who can be register or login the application.
 * Two Users are the same if they have the same ID.
 */
public interface User {

    /**
     * 
     * @return the name of the user that is unique and not null
     */
    String getUsername();

    /**
     * 
     * @return the Hashed password of the user
     */
    Optional<String> getHashedPassword();

    /**
     * 
     * @return the number of win match
     */
    int getWinCount();

    /**
     * 
     * @return the number of lost match
     */
    int getLostCount();

    /**
     * 
     * @return the number of draw match
     */
    int getDrawCount();

    /**
     * 
     * @return the number of match played
     */
    int getPlayedMatchCount();

    /**
     * Increase the win Count.
     */
    void increaseWinCount();

    /**
     * Increase the draw Count.
     */
    void increaseDrawCount();

    /**
     * Increase the lost Count.
     */
    void increaseLostCount();
}
