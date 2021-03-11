package jhaturanga.model.match;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import jhaturanga.commons.Pair;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.idgenerator.MatchIdGenerator;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.timer.Timer;

public class MatchImpl implements Match {

    private final String matchID;
    private final GameType gameType;
    private final Optional<Timer> timer;
    private final Collection<Player> players;
    private final History history;

    public MatchImpl(final GameType gameType, final Optional<Timer> timer) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.gameType = gameType;
        this.timer = timer;
        this.players = gameType.getGameController().getPlayers();
        this.history = new HistoryImpl(this.getBoard());
    }

    @Override
    public final String getMatchID() {
        return this.matchID;
    }

    @Override
    public final void start() {
        if (this.timer.isPresent()) {
            this.timer.get().start(this.getGameController().getPlayers().stream()
                    .filter(plr -> plr.getColor().equals(PlayerColor.WHITE)).findFirst().get());
        }
    }

    @Override
    public final MovementResult move(final Movement movement) {
        final MovementResult result = this.gameType.getMovementManager().move(movement);
        if (!result.equals(MovementResult.NONE)) {
            this.history.addMoveToHistory(
                    new MovementImpl(movement.getPieceInvolved(), movement.getOrigin(), movement.getDestination()));
            this.updateTimerStatus(movement.getPieceInvolved().getPlayer());
        }
        return result;
    }

    private void updateTimerStatus(final Player playerForOptionalTimeGain) {
        if (this.timer.isPresent()) {
            if (this.timer.get().getIncrement().isPresent()) {
                this.timer.get().addTimeToPlayer(playerForOptionalTimeGain, this.timer.get().getIncrement().get());
            }
            this.timer.get().switchPlayer(this.gameType.getMovementManager().getPlayerTurn());
        }
        if (this.isCompleted() && this.timer.isPresent()) {
            this.timer.get().stop();
        }
    }

    @Override
    public final boolean isCompleted() {
        return this.gameType.getGameController().isOver()
                || this.timer.isPresent() && this.timer.get().getPlayerWithoutTime().isPresent();
    }

    @Override
    public final Optional<Player> winner() {
        final Optional<Player> playerWonByCheckMate = this.players.stream()
                .filter(x -> this.gameType.getGameController().isWinner(x)).findAny();
        if (playerWonByCheckMate.isPresent()) {
            return playerWonByCheckMate;
        } else if (this.timer.isPresent() && this.timer.get().getPlayerWithoutTime().isPresent()) {
            return this.players.stream().filter(i -> this.timer.get().getRemaningTime(i) > 0).findAny();
        }
        return Optional.empty();
    }

    @Override
    public final Board getBoardAtIndexFromHistory(final int index) {
        return this.history.getBoardAtIndex(index);
    }

    @Override
    public final Board getBoard() {
        return this.gameType.getGameController().boardState();
    }

    @Override
    public final GameController getGameController() {
        return this.gameType.getGameController();
    }

    @Override
    public final Pair<Player, Integer> getPlayerTimeRemaining() {
        final Player player = this.gameType.getMovementManager().getPlayerTurn();
        final int timeRemaining = this.timer.get().getRemaningTime(player);
        return new Pair<>(player, timeRemaining);
    }

    @Override
    public final List<Board> getBoardFullHistory() {
        return this.history.getAllBoards();
    }

    @Override
    public final MovementManager getMovementManager() {
        return this.gameType.getMovementManager();
    }

}
