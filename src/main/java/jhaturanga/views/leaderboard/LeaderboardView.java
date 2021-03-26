package jhaturanga.views.leaderboard;

import jhaturanga.controllers.leaderboard.LeaderboardController;
import jhaturanga.views.JavaFXView;

public interface LeaderboardView extends JavaFXView {

    /**
     * 
     * @return the Leaderboard controller.
     */
    LeaderboardController getLeaderboardController();
}
