package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;

public interface LeaderboardController extends Controller {

    /**
     * 
     * @return the list of all Users.
     * @throws IOException
     */
    List<LeaderboardUserAdapter> getUsers() throws IOException;
}
