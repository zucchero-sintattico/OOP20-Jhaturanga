package jhaturanga.model.savedhistory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

public final class BoardState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final String matchID;

    private final Date date;

    private final User whiteUser;

    private final User blackUser;

    private final List<Board> boards;

    public BoardState(final String matchID, final Date date, final User whiteUser, final User blackUser,
            final List<Board> boards) {
        this.matchID = matchID;
        this.date = date;
        this.whiteUser = whiteUser;
        this.blackUser = blackUser;
        this.boards = boards;
    }

    public String getMatchID() {
        return matchID;
    }

    public Date getDate() {
        return date;
    }

    public User getWhiteUser() {
        return whiteUser;
    }

    public User getBlackUser() {
        return blackUser;
    }

    public List<Board> getBoards() {
        return boards;
    }

}
