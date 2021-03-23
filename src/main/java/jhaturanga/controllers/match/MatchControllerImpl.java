package jhaturanga.controllers.match;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.Pair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public final class MatchControllerImpl extends AbstractController implements MatchController {

    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private int moveCounter;
    private int index;

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

    @Override
    public Board getBoardStatus() {
        return this.getApplicationInstance().getMatch().get().getBoard();
    }

    @Override
    public Optional<Board> getPrevBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getMatch().get().getBoardAtIndexFromHistory(--this.index))
                : Optional.empty();
    }

    @Override
    public Optional<Board> getNextBoard() {
        return this.index < this.moveCounter
                ? Optional.of(this.getApplicationInstance().getMatch().get().getBoardAtIndexFromHistory(++this.index))
                : Optional.empty();
    }

    @Override
    public boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

    @Override
    public void start() {
        this.getApplicationInstance().getMatch().get().start();
    }

    private static String secondsToHumanReadableTime(final int seconds) {
        final int minutes = seconds / SECONDS_IN_ONE_MINUTE;
        final int secondsFromMinutes = seconds % SECONDS_IN_ONE_MINUTE;
        return String.format("%02d:%02d", minutes, secondsFromMinutes);
    }

    @Override
    public String getWhiteReminingTime() {
        return secondsToHumanReadableTime(this.getApplicationInstance().getMatch().get().getTimer()
                .getRemaningTime(this.getApplicationInstance().getMatch().get().getPlayers().getX()));
    }

    @Override
    public String getBlackReminingTime() {
        return secondsToHumanReadableTime(this.getApplicationInstance().getMatch().get().getTimer()
                .getRemaningTime(this.getApplicationInstance().getMatch().get().getPlayers().getY()));
    }

    @Override
    public MatchStatusEnum matchStatus() {
        return this.getApplicationInstance().getMatch().get().getMatchStatus();
    }

    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getApplicationInstance().getMatch().get().getPiecePossibleMoves(piece);
    }

    @Override
    public void saveMatch() throws IOException {
        // TODO: IMPLEMENT
//        if (this.getApplicationInstance().getGameType().isPresent()) {
//            final BoardState matchSaved = new BoardStateBuilder().date(new Date())
//                    .matchID(this.getApplicationInstance().getMatch().get().getMatchID())
//                    .whiteUser(this.getApplicationInstance().getFirstUser().get())
//                    .blackUser(this.getApplicationInstance().getSecondUser().get())
//                    .boards(this.getApplicationInstance().getMatch().get().getBoardFullHistory())
//                    .gameType(this.getApplicationInstance().getGameType().get()).build();
//
//            HistoryDataStorageStrategy.put(matchSaved, this.getApplicationInstance().getMatch().get().getMatchID());
//        }
    }

    @Override
    public Player getPlayerTurn() {
        return this.getApplicationInstance().getMatch().get().getMovementManager().getPlayerTurn();
    }

    @Override
    public void deleteMatch() {
        this.getApplicationInstance().deleteMatch();
    }

    @Override
    public Pair<Player, Player> getPlayers() {
        return this.getApplicationInstance().getMatch().get().getPlayers();
    }

}
