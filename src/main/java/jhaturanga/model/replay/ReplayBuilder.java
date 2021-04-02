package jhaturanga.model.replay;

import java.util.Date;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.user.User;

public final class ReplayBuilder {

    private String matchID;

    private Date date;

    private User whiteUser;

    private User blackUser;

    private List<Board> boards;

    private GameType gameType;

    public ReplayBuilder matchID(final String matchID) {
        this.matchID = matchID;
        return this;
    }

    public ReplayBuilder date(final Date date) {
        this.date = date;
        return this;
    }

    public ReplayBuilder whiteUser(final User whiUser) {
        this.whiteUser = whiUser;
        return this;
    }

    public ReplayBuilder blackUser(final User blackUser) {
        this.blackUser = blackUser;
        return this;

    }

    public ReplayBuilder boards(final List<Board> boards) {
        this.boards = boards;
        return this;
    }

    public ReplayBuilder gameType(final GameType gameType) {
        this.gameType = gameType;
        return this;
    }

    public Replay build() {
        return new Replay(matchID, date, whiteUser, blackUser, boards, gameType);
    }

}
