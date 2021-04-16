package jhaturanga.model.replay;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.user.User;

public final class ReplayData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final String matchID;

    private final Date date;

    private final User whiteUser;

    private final User blackUser;

    private final List<Board> boards;

    private final GameType gameType;

    public ReplayData(final String matchID, final Date date, final User whiteUser, final User blackUser,
            final List<Board> boards, final GameType gameType) {
        this.matchID = matchID;
        this.date = new Date(date.getTime());
        this.whiteUser = whiteUser;
        this.blackUser = blackUser;
        this.boards = boards;
        this.gameType = gameType;
    }

    public String getMatchID() {
        return this.matchID;
    }

    public Date getDate() {
        return new Date(this.date.getTime());
    }

    public User getWhiteUser() {
        return this.whiteUser;
    }

    public User getBlackUser() {
        return this.blackUser;
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    public GameType getGameType() {
        return this.gameType;
    }

}
