package jhaturanga.model.user;

public final class UserBuilderImpl implements UserBuilder {

    private final int id;
    private final String username;
    private int winCount;
    private int drawCount;
    private int lostCount;
    private boolean build;

    public UserBuilderImpl(final int id, final String username) {
        this.id = id;
        this.username = username;
        this.winCount = 0;
        this.drawCount = 0;
        this.lostCount = 0;
        this.build = false;
    }

    @Override
    public UserBuilder winCount(final int count) {
        this.winCount = count;
        return this;
    }

    @Override
    public UserBuilder drawCount(final int count) {
        this.drawCount = count;
        return this;
    }

    @Override
    public UserBuilder lostCount(final int count) {
        this.lostCount = count;
        return this;
    }

    @Override
    public User build() {
        if (build) {
            return null;
        }
        this.build = true;

        return new UserImpl(this.id,
                this.username,
                this.winCount,
                this.drawCount,
                this.lostCount);
    }

}
