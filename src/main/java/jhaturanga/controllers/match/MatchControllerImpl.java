package jhaturanga.controllers.match;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.MatchEndType;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.replay.ReplayBuilder;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.model.replay.SavedReplay;
import jhaturanga.model.replay.SavedReplayImpl;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.management.UsersManagerSingleton;

/**
 * Basic implementation of the MatchController.
 */
public final class MatchControllerImpl extends BasicController implements MatchController {

    private int index;

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementResult move(final BoardPosition origin, final BoardPosition destination) {

        if (this.getBoard().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.getBoard().getPieceAtPosition(origin).get();
            final MovementResult result = this.getApplicationInstance().getMatch().get()
                    .move(new PieceMovementImpl(piece, origin, destination));
            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.index = this.getApplicationInstance().getMatch().get().getHistory().getAllBoards().size() - 1;
            }
            return result;
        }
        return MovementResult.INVALID_MOVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return this.getApplicationInstance().getMatch().get().getBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getPreviousBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getMatch().get().getHistory().getBoardAtIndex(--this.index))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getNextBoard() {
        return this.index < this.getApplicationInstance().getMatch().get().getHistory().getAllBoards().size() - 1
                ? Optional.of(this.getApplicationInstance().getMatch().get().getHistory().getBoardAtIndex(++this.index))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMatch() throws IOException {
        if (this.getApplicationInstance().getMatch().isPresent()
                && !this.getApplicationInstance().getMatch().get().getGame().getType().equals(GameType.CHESS_PROBLEM)) {

            final ReplayData matchSaved = new ReplayBuilder().date(new Date())
                    .matchID(this.getApplicationInstance().getMatch().get().getMatchID())
                    .whiteUser(this.getApplicationInstance().getFirstUser().get())
                    .blackUser(this.getApplicationInstance().getSecondUser().get())
                    .boards(this.getApplicationInstance().getMatch().get().getHistory().getAllBoards())
                    .gameType(this.getApplicationInstance().getMatch().get().getGame().getType()).build();

            final SavedReplay replay = new SavedReplayImpl();
            replay.save(matchSaved);

            this.savePlayers();

        }
    }

    /**
     * Save the match data to the players.
     * 
     * @throws IOException
     */
    private void savePlayers() throws IOException {
        this.getApplicationInstance().getMatch().ifPresent(m -> {
            if (m.getMatchStatus().equals(MatchStatus.ENDED)) {
                if (m.getEndType().get().equals(MatchEndType.CHECKMATE)
                        || m.getEndType().get().equals(MatchEndType.TIMEOUT)
                        || m.getEndType().get().equals(MatchEndType.RESIGN)) {
                    m.getWinner().ifPresent(winner -> {
                        winner.getUser().increaseWinCount();
                        this.getPlayers().stream().filter(loser -> !loser.equals(winner)).findAny()
                                .ifPresent(p -> p.getUser().increaseLostCount());
                    });
                } else if (m.getEndType().get().equals(MatchEndType.DRAW)) {
                    this.getPlayers().getWhitePlayer().getUser().increaseDrawCount();
                    this.getPlayers().getBlackPlayer().getUser().increaseDrawCount();
                }
            }
        });
        UsersManagerSingleton.getInstance().put(this.getPlayers().getWhitePlayer().getUser());
        UsersManagerSingleton.getInstance().put(this.getPlayers().getBlackPlayer().getUser());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInNavigationMode() {
        return this.index != this.getApplicationInstance().getMatch().get().getHistory().getAllBoards().size() - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.getApplicationInstance().getMatch().get().start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWhiteRemainingTime() {
        return this.getApplicationInstance().getMatch().get().getTimer().getRemaningTime(this.getWhitePlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBlackRemainingTime() {
        return this.getApplicationInstance().getMatch().get().getTimer().getRemaningTime(this.getBlackPlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchStatus getStatus() {
        return this.getApplicationInstance().getMatch().get().getMatchStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getApplicationInstance().getMatch().get().getPiecePossibleMoves(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayerTurn() {
        return this.getApplicationInstance().getMatch().get().getGame().getMovementManager().getPlayerTurn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMatch() {
        this.getTimer().stop();
        this.getApplicationInstance().deleteMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerPair getPlayers() {
        return this.getApplicationInstance().getMatch().get().getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timer getTimer() {
        return this.getApplicationInstance().getMatch().get().getTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getWhitePlayer() {
        return this.getPlayers().getWhitePlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getBlackPlayer() {
        return this.getPlayers().getBlackPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopTimer() {
        this.getApplicationInstance().getMatch().get().getTimer().stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMatchPresent() {
        return this.getApplicationInstance().getMatch().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> getWinner() {
        return this.getApplicationInstance().getMatch().get().getWinner();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MatchEndType> getEndType() {
        return this.getApplicationInstance().getMatch().get().getEndType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resign(final Player player) {
        this.getApplicationInstance().getMatch().get().resign(player);
    }

}
