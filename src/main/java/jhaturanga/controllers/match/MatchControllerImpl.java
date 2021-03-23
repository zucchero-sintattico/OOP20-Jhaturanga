package jhaturanga.controllers.match;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.datastorage.HistoryDataStorageStrategy;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.savedhistory.BoardState;
import jhaturanga.model.savedhistory.BoardStateBuilder;

public class MatchControllerImpl extends AbstractController implements MatchController {

    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private int moveCounter;
    private int index;

    @Override
    public final MovementResult move(final BoardPosition origin, final BoardPosition destination) {
        if (this.getBoardStatus().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.getBoardStatus().getPieceAtPosition(origin).get();
            final MovementResult result = this.getApplicationInstance().getActualMatch().get()
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
    public final Board getBoardStatus() {
        return this.getApplicationInstance().getActualMatch().get().getBoard();
    }

    @Override
    public final Optional<Board> getPrevBoard() {
        return this.index > 0
                ? Optional.of(this.getApplicationInstance().getActualMatch().get().getBoardAtIndexFromHistory(--this.index))
                : Optional.empty();
    }

    @Override
    public final Optional<Board> getNextBoard() {
        return this.index < this.moveCounter
                ? Optional.of(this.getApplicationInstance().getActualMatch().get().getBoardAtIndexFromHistory(++this.index))
                : Optional.empty();
    }

    @Override
    public final boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

    @Override
    public final void start() {
        this.getApplicationInstance().getActualMatch().get().start();
    }

    private static String secondsToHumanReadableTime(final int seconds) {
        final int minutes = seconds / SECONDS_IN_ONE_MINUTE;
        final int secondsFromMinutes = seconds % SECONDS_IN_ONE_MINUTE;
        return String.format("%02d:%02d", minutes, secondsFromMinutes);
    }

    @Override
    public final String getWhiteReminingTime() {
        return secondsToHumanReadableTime(
                this.getApplicationInstance().getTimer().get().getRemaningTime(this.getApplicationInstance().getWhitePlayer().get()));
    }

    @Override
    public final String getBlackReminingTime() {
        return secondsToHumanReadableTime(
                this.getApplicationInstance().getTimer().get().getRemaningTime(this.getApplicationInstance().getBlackPlayer().get()));
    }

    @Override
    public final MatchStatusEnum matchStatus() {
        return this.getApplicationInstance().getActualMatch().get().matchStatus();
    }

    @Override
    public final Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getApplicationInstance().getActualMatch().get().getPiecePossibleMoves(piece);
    }

    @Override
    public final void saveMatch() throws IOException {
        if (this.getApplicationInstance().getGameType().isPresent()) {
            final BoardState matchSaved = new BoardStateBuilder().date(new Date())
                    .matchID(this.getApplicationInstance().getActualMatch().get().getMatchID())
                    .whiteUser(this.getApplicationInstance().getFirstUser().get()).blackUser(this.getApplicationInstance().getSecondUser().get())
                    .boards(this.getApplicationInstance().getActualMatch().get().getBoardFullHistory())
                    .gameType(this.getApplicationInstance().getGameType().get()).build();

            HistoryDataStorageStrategy.put(matchSaved, this.getApplicationInstance().getActualMatch().get().getMatchID());
        }
    }

    @Override
    public final Player getPlayerTurn() {
        return this.getApplicationInstance().getActualMatch().get().getMovementManager().getPlayerTurn();
    }

}
