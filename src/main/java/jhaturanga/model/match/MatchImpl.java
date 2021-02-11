package jhaturanga.model.match;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.game.GameType;
import jhaturanga.model.idgenerator.MatchIdGenerator;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;

public class MatchImpl implements Match {

    private final String matchID;
    private final GameType gameType;

    public MatchImpl(final GameType gameType, final Set<Player> players) {
        this.matchID = MatchIdGenerator.getNewMatchId();
        this.gameType = gameType;
    }

    @Override
    public String getMatchID() {
        return this.matchID;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean move(final Movement movement) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public final Optional<Player> winner() {
        return Optional.ofNullable(this.players.stream().filter(x -> this.gameController.isWinner(x)).findAny().get());
    }

    @Override
    public final Movement getMoveAtIndexFromHistory(final int index) {
        return null;
    }

}
