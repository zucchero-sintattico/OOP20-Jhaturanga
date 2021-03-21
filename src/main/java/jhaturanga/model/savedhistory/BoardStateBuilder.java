package jhaturanga.model.savedhistory;

import java.util.Date;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.user.User;

public final class BoardStateBuilder {

    private String matchID;

    private Date date;

    private User whiteUser;

    private User blackUser;

    private List<Board> boards;

    private GameTypesEnum gameType;

    public BoardStateBuilder matchID(final String matchID) {
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

    public BoardStateBuilder boards(final List<Board> boards) {
        this.boards = boards;
        return this;
    }

    public BoardStateBuilder gameType(final GameTypesEnum gameType) {
        this.gameType = gameType;
        return this;
    }

    public BoardState build() {
        return new BoardState(matchID, date, whiteUser, blackUser, boards, gameType);
    }

}
