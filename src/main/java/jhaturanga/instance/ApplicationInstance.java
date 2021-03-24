package jhaturanga.instance;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.match.Match;
import jhaturanga.model.replay.Replay;
import jhaturanga.model.user.User;

/**
 */
public interface ApplicationInstance {

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
     * 
     * @param replay
     */
    void setReplay(Replay replay);

    /**
     * 
     * @return the replay.
     */
    Optional<Replay> getReplay();

    /**
     * Delete the replay.
     */
    void deleteReplay();

}
