package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.Pair;
import jhaturanga.commons.network.NetworkManager;
import jhaturanga.commons.network.NetworkManagerImpl;
import jhaturanga.commons.network.NetworkMatchData;
import jhaturanga.commons.network.NetworkMovement;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public final class NetworkMatch implements Match {

    private NetworkManager network;
    private String matchID;

    public NetworkMatch() {
        try {
            this.network = new NetworkManagerImpl(this::onMovement);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void join(final String matchID) {
        this.matchID = matchID;
        this.network.joinMatch(matchID);
        this.waitUntilSetupData();
    }

    public void create(final GameTypesEnum game, final Player player, final Timer timer) {
        final NetworkMatchData data = new NetworkMatchData(game, player.getUserName());
        this.matchID = this.network.createMatch(data);
        this.waitUntilPlayerJoin();
    }

    private void waitUntilPlayerJoin() {

        final String playerName = this.network.getJoinedUserName();
        System.out.println("finally a player joined : " + playerName);
    }

    private void waitUntilSetupData() {

        final NetworkMatchData data = this.network.getMatchData();

        final String playerName = data.getPlayerName();
        final GameTypesEnum game = data.getGameType();

        System.out.println("DATA RECEIVED : PLAYER = " + playerName + " GAME = " + game);
    }

    private void onMovement(final NetworkMovement movement) {
        System.out.println("MOVEMENT : " + movement);
    }

    @Override
    public String getMatchID() {
        return this.matchID;
    }

    @Override
    public void start() {

    }

    @Override
    public MovementResult move(final Movement movement) {
        return null;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public Optional<Player> winner() {
        return Optional.empty();
    }

    @Override
    public Board getBoardAtIndexFromHistory(final int index) {
        return null;
    }

    @Override
    public Board getBoard() {
        return null;
    }

    @Override
    public GameController getGameController() {
        return null;
    }

    @Override
    public Pair<Player, Integer> getPlayerTimeRemaining() {
        return null;
    }

    @Override
    public List<Board> getBoardFullHistory() {
        // TODO Auto-generated method stub
        return null;
    }

}
