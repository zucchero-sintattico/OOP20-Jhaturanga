package jhaturanga.model.match;

import java.util.Collection;
import java.util.Optional;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.idgenerator.MatchIdGenerator;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public class MatchImpl implements Match {

    private final String matchID;
    private final GameType gameType;
    private final Optional<Timer> timer;
    private final Collection<Player> players;
    private final History history = new HistoryImpl();
    // TODO: DA DEFINIRE COME GESTIRE I PLAYERS DEL MATCH/GAMETYPE
    // --> CHI DEVE TENERLI? --> COME EVITARE POSSIBILI ERRORI AVENDO PLAYERS
    // DIVERSI FRA MATCH E GAMETYPE E RIDONDANZA?

    public MatchImpl(final GameType gameType, final Optional<Timer> timer, final Collection<Player> players) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.gameType = gameType;
        this.timer = timer;
        this.players = players;
    }

    @Override
    public final String getMatchID() {
        return this.matchID;
    }

    @Override
    public void start() {
        // TODO: DEFINIRE LA START NEL MATCH
    }

    @Override
    public final boolean move(final Movement movement) {
        if (this.gameType.getMovementManager().move(movement)) {
            this.history.addMoveToHistory(movement);
            return true;
        }
        return false;
    }

    @Override
    public final boolean isCompleted() {
        return this.gameType.getGameController().isOver();
    }

    @Override
    public final Optional<Player> winner() {
        return Optional
                .ofNullable(this.players.stream().filter(x -> this.gameType.getGameController().isWinner(x)).findAny().get());
    }

    @Override
    public final Movement getMoveAtIndexFromHistory(final int index) {
        return this.history.getMoveAtIndex(index);
    }

    @Override
    public final Board getBoard() {
        return this.gameType.getGameController().boardState();
    }

}
