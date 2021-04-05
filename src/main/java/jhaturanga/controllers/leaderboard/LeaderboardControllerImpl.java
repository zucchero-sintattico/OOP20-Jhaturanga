package jhaturanga.controllers.leaderboard;

import java.io.IOException;
import java.util.List;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;
import jhaturanga.model.leaderboard.builder.LeaderboardBuilderImpl;
import jhaturanga.model.user.management.UsersManagerSingleton;

public final class LeaderboardControllerImpl extends AbstractController implements LeaderboardController {

    private static final int WIN_POINT = 5;
    private static final int DRAW_POINT = 2;
    private static final int LOSE_POINT = -1;

    @Override
    public List<LeaderboardUserAdapter> getUsers() throws IOException {
        return new LeaderboardBuilderImpl(
                (u1, u2) -> u1.getUsername().compareTo(u2.getUsername()),
                u -> u.getWinCount() * WIN_POINT
                    + u.getDrawCount() * DRAW_POINT
                    + u.getLostCount() * LOSE_POINT)
                .addUsers(UsersManagerSingleton.getInstance().getAllUsers())
                .build()
                .getUsers();
    }

}
