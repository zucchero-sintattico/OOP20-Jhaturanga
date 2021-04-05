package jhaturanga.model.leaderboard.builder;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

import jhaturanga.model.leaderboard.Leaderboard;
import jhaturanga.model.leaderboard.strategy.ScoreStrategy;
import jhaturanga.model.user.User;

/**
 *
 * An interface that fulfill the Builder pattern to construct a Leaderboard.
 */
public interface LeaderboardBuilder {

    /**
     * 
     * @param users for creating a Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder addUsers(Collection<User> users);

    /**
     * 
     * @param predicate for filter in the Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder addFilter(Predicate<User> predicate);

    /**
     * 
     * @param comparator for ordering the Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder comparator(Comparator<User> comparator);

    /**
     * 
     * @param strategy to use for scoring users
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder strategy(ScoreStrategy strategy);

    /**
     * 
     * @return the Leaderboard built
     */
    Leaderboard build();
}
