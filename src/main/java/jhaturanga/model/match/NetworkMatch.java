package jhaturanga.model.match;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.Pair;
import jhaturanga.commons.network.NetworkManager;
import jhaturanga.commons.network.NetworkManagerImpl;
import jhaturanga.commons.network.NetworkMatchData;
import jhaturanga.commons.network.NetworkMovement;
import jhaturanga.commons.network.ObjectSerializer;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

public final class NetworkMatch implements Match {

    // Network connection
    private NetworkManager network;
    private String matchID;

    private final User localUser;
    private Player localPlayer;
    private NetworkMatchData data;

    private Match match;

    /**
     * Setup a NetworkMatch.
     * 
     * @param user - the user
     */
    public NetworkMatch(final User user) {
        this.localUser = user;
        try {
            this.network = new NetworkManagerImpl(this::onMovement);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void join(final String matchID) {
        this.localPlayer = new PlayerImpl(PlayerColor.BLACK, this.localUser);
        this.matchID = matchID;
        this.network.joinMatch(matchID, this.localPlayer);
        this.waitUntilSetupData();
    }

    public String create(final GameTypesEnum game, final Timer timer) {
        this.localPlayer = new PlayerImpl(PlayerColor.WHITE, this.localUser);
        this.data = new NetworkMatchData(game, this.localPlayer);
        this.matchID = this.network.createMatch(this.data);
        return this.matchID;
    }

    public void waitUntilPlayerJoin() {
        Player player;
        try {
            player = (Player) ObjectSerializer.fromString(this.network.getJoinedUserName());
            this.match = new MatchImpl(this.data.getGameType().getGameType(this.localPlayer, player), Optional.empty());
            System.out.println("finally a player joined : " + player);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    private void waitUntilSetupData() {

        final NetworkMatchData data = this.network.getMatchData();
        final Player player = data.getPlayer();
        final GameTypesEnum game = data.getGameType();
        this.match = new MatchImpl(game.getGameType(player, this.localPlayer), Optional.empty());
        System.out.println("DATA RECEIVED : PLAYER = " + player + " GAME = " + game);
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
        this.match.start();
    }

    @Override
    public MovementResult move(final Movement movement) {
        return this.match.move(movement);
    }

    @Override
    public boolean isCompleted() {
        return this.match.isCompleted();
    }

    @Override
    public Optional<Player> winner() {
        return this.match.winner();
    }

    @Override
    public Board getBoardAtIndexFromHistory(final int index) {
        return this.match.getBoardAtIndexFromHistory(index);
    }

    @Override
    public Board getBoard() {
        return this.match.getBoard();
    }

    @Override
    public GameController getGameController() {
        return this.match.getGameController();
    }

    @Override
    public Pair<Player, Integer> getPlayerTimeRemaining() {
        return this.match.getPlayerTimeRemaining();
    }

    @Override
    public List<Board> getBoardFullHistory() {
        return this.match.getBoardFullHistory();
    }

}
