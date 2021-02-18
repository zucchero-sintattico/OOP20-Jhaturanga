package jhaturanga.model.match;

import java.util.Collection;
import java.util.Optional;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.idgenerator.MatchIdGenerator;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.timer.Timer;

public class MatchImpl implements Match {

    private final String matchID;
    private final GameType gameType;
    private final Optional<Timer> timer;
    private final Collection<Player> players;
    private final History history;
    // TODO: DA DEFINIRE COME GESTIRE I PLAYERS DEL MATCH/GAMETYPE
    // --> CHI DEVE TENERLI? --> COME EVITARE POSSIBILI ERRORI AVENDO PLAYERS
    // DIVERSI FRA MATCH E GAMETYPE E RIDONDANZA?

    public MatchImpl(final GameType gameType, final Optional<Timer> timer, final Collection<Player> players) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.gameType = gameType;
        this.timer = timer;
        this.players = players;
        this.history = new HistoryImpl(this.getBoard());
    }

    @Override
    public final String getMatchID() {
        return this.matchID;
    }

    @Override
    public final void start() {
        if (this.timer.isPresent()) {
            this.timer.get()
                    .start(players.stream().filter(plr -> plr.getColor().equals(PlayerColor.WHITE)).findFirst().get());
        }
    }

    @Override
    public final boolean move(final Movement movement) {
        if (this.gameType.getMovementManager().move(movement)) {
            this.history.addMoveToHistory(
                    new MovementImpl(movement.getPieceInvolved(), movement.getOrigin(), movement.getDestination()));
            return true;
        }

        return false;
    }

    @Override
    public final boolean isCompleted() {
        return this.gameType.getGameController().isOver() || this.timer.get().getRemaningTime(player);
    }

    @Override
    public final Optional<Player> winner() {
        return Optional.ofNullable(
                this.players.stream().filter(x -> this.gameType.getGameController().isWinner(x)).findAny().get());
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
    public Player getPlayerTurn() {
        return this.timer.get().getRemaningTime(
                players.stream().filter(plr -> plr.getColor().equals(PlayerColor.WHITE).findFirst().get());
    }

}
