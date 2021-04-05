package jhaturanga.model.leaderboard.strategy;

import jhaturanga.model.user.User;

@FunctionalInterface
public interface ScoreStrategy {

    /**
     * 
     * @param user to calculate the score
     * @return the score of the user
     */
    int getScore(User user);
}
