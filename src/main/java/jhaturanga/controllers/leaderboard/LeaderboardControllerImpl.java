package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jhaturanga.commons.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManagerImpl;

public final class LeaderboardControllerImpl extends AbstractController implements LeaderboardController {

    @Override
    public List<User> getUsers() throws IOException {
        return new ArrayList<>(new UsersManagerImpl(new UsersDataStorageJsonStrategy()).getAllUsers());
    }

}
