package jhaturanga.model.replay;

import java.util.Date;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.user.User;

/**
 * The Class ReplayBuilder.
 */
public final class ReplayBuilder {

    /** The match ID. */
    private String matchID;

    /** The date. */
    private Date date;

    /** The white user. */
    private User whiteUser;

    /** The black user. */
    private User blackUser;

    /** The boards. */
    private List<Board> boards;

    /** The game type. */
    private GameType gameType;

    /**
     * Match ID.
     *
     * @param matchID the match ID
     * @return the replay builder
     */
    public ReplayBuilder matchID(final String matchID) {
        this.matchID = matchID;
        return this;
    }

    /**
     * Date.
     *
     * @param date the date
     * @return the replay builder
     */
    public ReplayBuilder date(final Date date) {
        this.date = new Date(date.getTime());
        return this;
    }

    /**
     * White user.
     *
     * @param whiUser the white user
     * @return the replay builder
     */
    public ReplayBuilder whiteUser(final User whiUser) {
        this.whiteUser = whiUser;
        return this;
    }

    /**
     * Black user.
     *
     * @param blackUser the black user
     * @return the replay builder
     */
    public ReplayBuilder blackUser(final User blackUser) {
        this.blackUser = blackUser;
        return this;

    }

    /**
     * Boards.
     *
     * @param boards the boards
     * @return the replay builder
     */
    public ReplayBuilder boards(final List<Board> boards) {
        this.boards = boards;
        return this;
    }

    /**
     * Game type.
     *
     * @param gameType the game type
     * @return the replay builder
     */
    public ReplayBuilder gameType(final GameType gameType) {
        this.gameType = gameType;
        return this;
    }

    /**
     * Build.
     *
     * @return the replay data
     */
    public ReplayData build() {
        return new ReplayData(matchID, date, whiteUser, blackUser, boards, gameType);
    }

}
