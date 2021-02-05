package jhaturanga.model.user;

public final class UserImpl implements User {

    private final int id;
    private final String username;
    private int winCount;
    private int drawCount;
    private int lostCount;

    public UserImpl(final int id, final String username, 
            final int winCount, final int drawCount, final int lostCount) {
        this.id = id;
        this.username = username;
        this.winCount = winCount;
        this.drawCount = drawCount;
        this.lostCount = lostCount;
    }

    @Override
    public int getUserID() {
        return this.id;
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public int getWinCount() {
        return this.winCount;
    }

    @Override
    public int getLostCount() {
        return this.lostCount;
    }

    @Override
    public int getDrawCount() {
        return this.drawCount;
    }

    @Override
    public int getPlayedMatchCount() {
        return this.winCount + this.drawCount + this.lostCount;
    }

    @Override
    public void increaseWinCount() {
        this.winCount++;
    }

    @Override
    public void increaseDrawCount() {
        this.drawCount++;
    }

    @Override
    public void increaseLostCount() {
        this.lostCount++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserImpl other = (UserImpl) obj;
        return id == other.id;
    }

}
