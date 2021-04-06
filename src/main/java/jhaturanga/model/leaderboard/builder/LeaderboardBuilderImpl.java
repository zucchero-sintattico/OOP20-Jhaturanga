package jhaturanga.model.leaderboard.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jhaturanga.model.leaderboard.Leaderboard;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapter;
import jhaturanga.model.leaderboard.adapter.LeaderboardUserAdapterImpl;
import jhaturanga.model.leaderboard.strategy.ScoreStrategy;
import jhaturanga.model.user.User;

/**
 * An implementation of {@link LeaderboardBuilder}.
 *
 */
public final class LeaderboardBuilderImpl implements LeaderboardBuilder {

    private final List<User> users = new ArrayList<>();
    private Predicate<User> tests = u -> true;
    private Comparator<User> comparator;
    private ScoreStrategy strategy;

    private boolean built;

    public LeaderboardBuilderImpl(final Comparator<User> comparator, final ScoreStrategy strategy) {
        this.comparator = comparator;
        this.strategy = strategy;
    }

    @Override
    public LeaderboardBuilder addUsers(final Collection<User> users) {
        this.users.addAll(users);
        return this;
    }

    @Override
    public LeaderboardBuilder addFilter(final Predicate<User> predicate) {
        this.tests = tests.and(predicate);
        return this;
    }

    @Override
    public LeaderboardBuilder comparator(final Comparator<User> comparator) {
        this.comparator = comparator;
        return this;
    }

    @Override
    public LeaderboardBuilder strategy(final ScoreStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    @Override
    public Leaderboard build() {
        if (built) {
            throw new IllegalStateException("Do not reuse the Builder");
        }

        Objects.requireNonNull(this.comparator);
        Objects.requireNonNull(this.strategy);

        built = true;

        return new LeaderboardImpl(
                this.users.stream()
                .filter(this.tests)
                .sorted(this.comparator)
                .collect(Collectors.toUnmodifiableList()));
    }

    public final class LeaderboardImpl implements Leaderboard {

        private final List<LeaderboardUserAdapter> users;

        public LeaderboardImpl(final List<User> users) {
            this.users = users.stream()
                    .map(u -> new LeaderboardUserAdapterImpl(u, strategy.getScore(u)))
                    .collect(Collectors.toUnmodifiableList());
        }

        @Override
        public List<LeaderboardUserAdapter> getUsers() {
            return new ArrayList<>(this.users);
        }

    }

}
