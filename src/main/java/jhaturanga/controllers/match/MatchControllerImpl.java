package jhaturanga.controllers.match;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.commons.datastorage.HistoryDataStorageStrategy;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.replay.Replay;
import jhaturanga.model.replay.ReplayBuilder;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.management.UsersManagerSingleton;

public class MatchControllerImpl extends AbstractController implements MatchController {

    private int moveCounter;
    private int index;

    /**
     * 
     */
    @Override
    public MovementResult move(final BoardPosition origin, final BoardPosition destination) {

        if (this.getBoardStatus().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.getBoardStatus().getPieceAtPosition(origin).get();
            final MovementResult result = this.getApplicationInstance().getMatch().get()
                    .move(new MovementImpl(piece, origin, destination));
            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.moveCounter++;
                this.index = this.moveCounter;
            }
            return result;
        }
        return MovementResult.INVALID_MOVE;
    }

    /**
     * 
     */
    @Override
    public Board getBoardStatus() {
        return this.getApplicationInstance().getMatch().get().getBoard();
    }

    /**
     * 
     */
    @Override
    public Optional<Board> getPreviousBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getMatch().get().getBoardAtIndexFromHistory(--this.index))
                : Optional.empty();
    }

    /**
     * 
     */
    @Override
    public Optional<Board> getNextBoard() {
        return this.index < this.moveCounter
                ? Optional.of(this.getApplicationInstance().getMatch().get().getBoardAtIndexFromHistory(++this.index))
                : Optional.empty();
    }

    /**
     * 
     */
    @Override
    public void saveMatch() throws IOException {

        final Replay matchSaved = new ReplayBuilder().date(new Date())
                .matchID(this.getApplicationInstance().getMatch().get().getMatchID())
                .whiteUser(this.getApplicationInstance().getFirstUser().get())
                .blackUser(this.getApplicationInstance().getSecondUser().get())
                .boards(this.getApplicationInstance().getMatch().get().getBoardFullHistory())
                .gameType(this.getApplicationInstance().getMatch().get().getType()).build();
        HistoryDataStorageStrategy.put(matchSaved, this.getApplicationInstance().getMatch().get().getMatchID());

        if (this.getApplicationInstance().getMatch().isPresent()
                && this.getApplicationInstance().getMatch().get().getType() != GameTypesEnum.CHESS_PROBLEM) {
            this.savePlayers();
        }
    }

    private void savePlayers() throws IOException {
        this.getApplicationInstance().getMatch().ifPresent(m -> {
            if (m.getMatchStatus() != MatchStatusEnum.ACTIVE) {
               if (m.getMatchStatus() == MatchStatusEnum.CHECKMATE 
                       || m.getMatchStatus() == MatchStatusEnum.ENDED_FOR_TIME) {
                   m.getWinner().ifPresent(winner -> {
                       winner.getUser().increaseWinCount();
                       Stream.of(this.getPlayers().getX(), this.getPlayers().getY())
                           .filter(loser -> !loser.equals(winner))
                           .findAny().ifPresent(p -> p.getUser().increaseLostCount());
                   });
               } else if (m.getMatchStatus() == MatchStatusEnum.DRAW) {
                   this.getPlayers().getX().getUser().increaseDrawCount();
                   this.getPlayers().getY().getUser().increaseDrawCount();
               }
            }
        });
        UsersManagerSingleton.getInstance().put(this.getPlayers().getX().getUser());
        UsersManagerSingleton.getInstance().put(this.getPlayers().getY().getUser());
    }

    /**
     * 
     */
    @Override
    public boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

    /**
     * 
     */
    @Override
    public void start() {
        this.getApplicationInstance().getMatch().get().start();
    }

    /**
     * 
     */
    @Override
    public double getWhiteRemainingTime() {
        return this.getApplicationInstance().getMatch().get().getTimer().getRemaningTime(this.getWhitePlayer());
    }

    /**
     * 
     */
    @Override
    public double getBlackRemainingTime() {
        return this.getApplicationInstance().getMatch().get().getTimer().getRemaningTime(this.getBlackPlayer());
    }

    /**
     * 
     */
    @Override
    public MatchStatusEnum matchStatus() {
        return this.getApplicationInstance().getMatch().get().getMatchStatus();
    }

    /**
     * 
     */
    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getApplicationInstance().getMatch().get().getPiecePossibleMoves(piece);
    }

    /**
     * 
     */
    @Override
    public Player getPlayerTurn() {
        return this.getApplicationInstance().getMatch().get().getMovementManager().getPlayerTurn();
    }

    /**
     * 
     */
    @Override
    public void deleteMatch() {
        this.getTimer().stop();
        this.getApplicationInstance().deleteMatch();
    }

    /**
     * 
     */
    @Override
    public Pair<Player, Player> getPlayers() {
        return this.getApplicationInstance().getMatch().get().getPlayers();
    }

    /**
     * 
     */
    @Override
    public Timer getTimer() {
        return this.getApplicationInstance().getMatch().get().getTimer();
    }

    /**
     * 
     */
    @Override
    public Player getWhitePlayer() {
        return this.getPlayers().getX();
    }

    /**
     * 
     */
    @Override
    public Player getBlackPlayer() {
        return this.getPlayers().getY();
    }

    /**
     * 
     */
    @Override
    public void stopTimer() {
        this.getApplicationInstance().getMatch().get().getTimer().stop();
    }

    /**
     * 
     */
    @Override
    public boolean isMatchPresent() {
        return this.getApplicationInstance().getMatch().isPresent();
    }

}
