package jhaturanga.model.match;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.Pair;
import jhaturanga.commons.network.NetworkManager;
import jhaturanga.commons.network.NetworkManagerImpl;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.history.HistoryImpl;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public final class NetworkMatch implements Match {

    private NetworkManager network;
    private final String matchID;
    private final GameType gameType;
    private final Optional<Timer> timer;
    private final Collection<Player> players;
    private final History history;

    public NetworkMatch(final String matchID, final GameType gameType, final Optional<Timer> timer)
            throws MqttException {
        this.matchID = matchID;
        this.gameType = gameType;
        this.timer = timer;
        this.players = gameType.getGameController().getPlayers();
        this.history = new HistoryImpl(this.getBoard());

        this.network = new NetworkManagerImpl();
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
    public MovementResult move(final Movement movement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Player> winner() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getBoardAtIndexFromHistory(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameController getGameController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<Player, Integer> getPlayerTimeRemaining() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Board> getBoardFullHistory() {
        // TODO Auto-generated method stub
        return null;
    }

}
