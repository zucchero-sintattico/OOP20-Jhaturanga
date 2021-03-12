package jhaturanga.model.savedhistory;

import java.util.Date;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.user.User;

public final class BoardStateBuilder {

    private int matchID;

    private Date date;

    private User whiteUser;

    private User blackUser;

    private List<Board> boards;

    public BoardStateBuilder matchID(final int matchID) {
        this.matchID = matchID;
        return this;
    }

    public BoardStateBuilder date(final Date date) {
        this.date = date;
        return this;
    }

    public BoardStateBuilder whiteUser(final User whiUser) {
        this.whiteUser = whiUser;
        return this;
    }

    public BoardStateBuilder blackUser(final User blackUser) {
        this.blackUser = blackUser;
        return this;

    }

    public BoardState build() {
        return new BoardState(matchID, date, whiteUser, blackUser, boards);
    }

}
