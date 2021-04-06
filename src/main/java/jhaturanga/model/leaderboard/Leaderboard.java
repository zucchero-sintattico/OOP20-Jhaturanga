package jhaturanga.model.leaderboard;

import java.util.List;

import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;

/**
 * An interface that represent the Leaderboard of all users.
 *
 */
public interface Leaderboard {

    /**
     * 
     * @return the leaderboard of all Users
     */
    List<LeaderboardUserAdapter> getUsers();
}
