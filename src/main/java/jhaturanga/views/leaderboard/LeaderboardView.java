package jhaturanga.views.leaderboard;

import jhaturanga.controllers.leaderboard.LeaderboardController;
import jhaturanga.views.View;

public interface LeaderboardView extends View {

    /**
     * 
     * @return the Leaderboard controller.
     */
    LeaderboardController getLeaderboardController();
}
