package jhaturanga.model.leaderboard.adapter;

import jhaturanga.model.user.User;

public final class LeaderboardUserAdapterImpl implements LeaderboardUserAdapter {

    private final User user;
    private final int score;

    public LeaderboardUserAdapterImpl(final User user, final int score) {
        this.user = user;
        this.score = score;
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public int getWinCount() {
        return this.user.getWinCount();
    }

    @Override
    public int getLostCount() {
        return this.user.getLostCount();
    }

    @Override
    public int getDrawCount() {
        return this.user.getDrawCount();
    }

    @Override
    public int getPlayedMatchCount() {
        return this.user.getPlayedMatchCount();
    }

    @Override
    public int getScore() {
        return this.score;
    }

}
