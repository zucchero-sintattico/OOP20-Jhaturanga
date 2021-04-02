package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.Pair;
import jhaturanga.commons.PlayerPair;
import jhaturanga.commons.network.NetworkMatchData;
import jhaturanga.commons.network.NetworkMatchManager;
import jhaturanga.commons.network.NetworkMatchManagerImpl;
import jhaturanga.commons.network.NetworkMovement;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.manager.MovementManager;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimersEnum;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

public final class NetworkMatch implements Match {

    // Network connection
    private NetworkMatchManager network;
    private String matchID;

    private final User localUser;
    private Player localPlayer;
    private Player otherPlayer;

    private NetworkMatchData data;

    private final Runnable onReady;
    private BiConsumer<Movement, MovementResult> onMovementHandler;

    private Match match;
    private final DefaultsTimersEnum timer = DefaultsTimersEnum.NO_LIMIT;

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

    public void disconnect() {
        System.out.println("NETWORK DISCONNECT");
        this.network.disconnect();
    }

    public void setOnMovementHandler(final BiConsumer<Movement, MovementResult> onMovementHandler) {
        this.onMovementHandler = onMovementHandler;
    }

    public boolean isWhitePlayer() {
        return this.localPlayer.getColor().equals(PlayerColor.WHITE);
    }

    public Player getWhitePlayer() {
        return this.localPlayer.getColor().equals(PlayerColor.WHITE) ? this.localPlayer : this.otherPlayer;
    }

    public Player getBlackPlayer() {
        return this.localPlayer.getColor().equals(PlayerColor.BLACK) ? this.localPlayer : this.otherPlayer;
    }

    private void onDataReceived() {

        final NetworkMatchData data = this.network.getMatchData();
        this.otherPlayer = data.getPlayer();
        final GameTypesEnum game = data.getGameType();

        final PlayerPair players = new PlayerPair(this.otherPlayer, this.localPlayer);
        this.match = new MatchBuilderImpl().gameType(data.getGameType().getGeneratedGameType(players))
                .timer(data.getTimer().getTimer(players)).build();

        System.out.println("DATA RECEIVED : PLAYER = " + this.otherPlayer + " GAME = " + game);
        Optional.ofNullable(this.onReady).ifPresent(Runnable::run);
    }

    private void onUserJoined() {

        this.otherPlayer = this.network.getJoinedPlayer();
        final PlayerPair players = new PlayerPair(this.localPlayer, this.otherPlayer);
        this.match = new MatchBuilderImpl().gameType(this.data.getGameType().getGeneratedGameType(players))
                .timer(this.data.getTimer().getTimer(players)).build();

        System.out.println("finally a player joined : " + this.otherPlayer);
        Optional.ofNullable(this.onReady).ifPresent(Runnable::run);
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
     * @param gameType
     * @return the match id
     */
    public String create(final GameTypesEnum gameType) {
        // For now the player which create is the white player.
        this.localPlayer = new PlayerImpl(PlayerColor.WHITE, this.localUser);

        // Setup the game data
        this.data = new NetworkMatchData(gameType, this.timer, this.localPlayer);

        // Create the match and return the match id
        this.matchID = this.network.createMatch(this.data, this::onUserJoined);
        return this.matchID;
    }

    private void onMovement(final NetworkMovement movement) {
        System.out.println("PLAYER = " + this.localPlayer + " MOVEMENT : " + movement);

        final Movement realMovement = new MovementImpl(this.getBoard().getPieceAtPosition(movement.getOrigin()).get(),
                movement.getDestination());

        final MovementResult res = this.match.move(realMovement);
        if (!res.equals(MovementResult.INVALID_MOVE)) {
            this.onMovementHandler.accept(realMovement, res);
        }

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
        System.out.println("CALL REAL MOVEMENT METHOD");
        final MovementResult res = this.match.move(movement);
        if (!res.equals(MovementResult.INVALID_MOVE)) {
            this.network.sendMove(movement);
        }
        return res;

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

    @Override
    public MovementManager getMovementManager() {
        return this.match.getMovementManager();
    }

    @Override
    public GameTypesEnum getType() {
        return this.match.getType();
    }

    @Override
    public Timer getTimer() {
        return this.match.getTimer();
    }

    @Override
    public PlayerPair getPlayers() {
        return this.match.getPlayers();
    }

    @Override
    public MatchStatusEnum getMatchStatus() {
        return this.match.getMatchStatus();
    }

    @Override
    public Optional<Player> getWinner() {
        return this.match.getWinner();
    }

    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.match.getPiecePossibleMoves(piece);
    }

    @Override
    public void uploadMatchHistory(final List<Board> boardHistory) {
        this.match.uploadMatchHistory(boardHistory);
    }

}
