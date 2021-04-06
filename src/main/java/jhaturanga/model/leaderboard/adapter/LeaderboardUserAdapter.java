package jhaturanga.model.leaderboard.adapter;

/**
 * An adapter interface for adapting {@link jhaturanga.model.user.User} to the Leaderboard.
 * 
 */
public interface LeaderboardUserAdapter {

    /**
     * 
     * @return the name of the user that is unique and not null
     */
    String getUsername();

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
     * 
     * @return the score of the user
     */
    int getScore();
}
