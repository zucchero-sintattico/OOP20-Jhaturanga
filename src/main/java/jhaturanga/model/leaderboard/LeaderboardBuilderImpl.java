package jhaturanga.model.leaderboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jhaturanga.model.user.User;

public final class LeaderboardBuilderImpl implements LeaderboardBuilder {

    private final List<User> users = new ArrayList<>();
    private Predicate<User> tests = u -> true;
    private Comparator<User> comparator;

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
    public List<User> build() {
        return this.users
                .stream()
                .filter(this.tests)
                .sorted(this.comparator)
                .collect(Collectors.toUnmodifiableList());
    }


}
