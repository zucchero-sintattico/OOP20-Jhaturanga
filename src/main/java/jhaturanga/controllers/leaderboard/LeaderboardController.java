package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.user.User;

public interface LeaderboardController extends Controller {

    List<User> getUsers() throws IOException;
}
