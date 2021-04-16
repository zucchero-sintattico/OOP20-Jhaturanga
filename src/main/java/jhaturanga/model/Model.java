package jhaturanga.model;

import java.util.Optional;

import jhaturanga.model.match.Match;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.model.user.User;

/**
 * The Model access. It save the actual state of the application, including the
 * logged users, the actual match and the actual replay.
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

    /**
     * Match Management
     */

    /**
     * Set the match.
     * 
     * @param match - the match to be setted
     */
    void setMatch(Match match);

    /**
     * @return the actual matches if presents.
     */
    Optional<Match> getMatch();

    /**
     * Delete the match.
     */
    void deleteMatch();

    /**
     * Replay Management
     */

    /**
     * Set the replay.
     * 
     * @param replay - the replay to be setted
     */
    void setReplay(ReplayData replay);

    /**
     * Get the actual replay.
     * 
     * @return the actual replay if present
     */
    Optional<ReplayData> getReplay();

    /**
     * Delete the replay.
     */
    void deleteReplay();

}
