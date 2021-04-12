package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;

/**
 * The controller for the leaderboard page view.
 */
public interface LeaderboardController extends Controller {

    /**
     * 
     * @return the list of all Users.
     * @throws IOException
     */
    List<LeaderboardUserAdapter> getUsers() throws IOException;
}
