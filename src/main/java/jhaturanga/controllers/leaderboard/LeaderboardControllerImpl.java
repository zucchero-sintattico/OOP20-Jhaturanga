package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManagerSingleton;

public final class LeaderboardControllerImpl extends AbstractController implements LeaderboardController {

    @Override
    public List<User> getUsers() throws IOException {
        return new ArrayList<>(UsersManagerSingleton.getInstance().getAllUsers());
    }

}
