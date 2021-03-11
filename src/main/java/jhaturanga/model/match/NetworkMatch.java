package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.Pair;
import jhaturanga.commons.network.NetworkMatchData;
import jhaturanga.commons.network.NetworkMatchManager;
import jhaturanga.commons.network.NetworkMatchManagerImpl;
import jhaturanga.commons.network.NetworkMovement;
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
    private NetworkMatchManager network;
    private String matchID;

    private final User localUser;
    private Player localPlayer;
    private NetworkMatchData data;

    private final Runnable onReady;

    private Match match;

    /**
     * Setup a NetworkMatch.
     * 
     * @param user    - the user
     * @param onReady - the callback to call when the game is ready
     */
    public NetworkMatch(final User user, final Runnable onReady) {
        // Setup the user
        this.localUser = user;
        // Setup onReady callback
        this.onReady = onReady;
        try {
            this.network = new NetworkMatchManagerImpl(this::onMovement);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void onDataReceived() {

        final NetworkMatchData data = this.network.getMatchData();
        final Player player = data.getPlayer();
        final GameTypesEnum game = data.getGameType();
        this.match = new MatchImpl(game.getGameType(player, this.localPlayer), Optional.empty());
        System.out.println("DATA RECEIVED : PLAYER = " + player + " GAME = " + game);

        this.onReady.run();
    }

    private void onUserJoined() {

        final Player player = this.network.getJoinedPlayer();
        this.match = new MatchImpl(this.data.getGameType().getGameType(this.localPlayer, player), Optional.empty());
        System.out.println("finally a player joined : " + player);

        this.onReady.run();
    }

    /**
     * Join a game.
     * 
     * @param matchID
     */
    public void join(final String matchID) {
        // For now the player which join is the black player.
        this.localPlayer = new PlayerImpl(PlayerColor.BLACK, this.localUser);
        this.matchID = matchID;
        this.network.joinMatch(matchID, this.localPlayer, this::onDataReceived);
    }

    /**
     * Create a game.
     * 
     * @param game  - the game type
     * @param timer - the timer
     * @return the match id
     */
    public String create(final GameTypesEnum game, final Timer timer) {
        // For now the player which create is the white player.
        this.localPlayer = new PlayerImpl(PlayerColor.WHITE, this.localUser);

        // Setup the game data
        this.data = new NetworkMatchData(game, this.localPlayer);

        // Create the match and return the match id
        this.matchID = this.network.createMatch(this.data, this::onUserJoined);
        return this.matchID;
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
