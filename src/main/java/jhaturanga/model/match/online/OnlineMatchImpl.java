package jhaturanga.model.match.online;

import java.util.Optional;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.network.NetworkMatchData;
import jhaturanga.commons.network.NetworkMatchManager;
import jhaturanga.commons.network.NetworkMatchManagerImpl;
import jhaturanga.commons.network.NetworkMovement;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.Game;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.history.History;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchEndType;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.player.pair.PlayerPairImpl;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;

public final class OnlineMatchImpl implements OnlineMatch {

    private final NetworkMatchManager network;
    private String matchID;

    private final User localUser;
    private Player localPlayer;
    private Player otherPlayer;

    private NetworkMatchData data;

    private final Runnable onReady;
    private MovementHandler onMovementHandler;

    private Match match;
    private final DefaultTimers timer = DefaultTimers.NO_LIMIT;

    /**
     * Setup a NetworkMatch.
     * 
     * @param user    - the user
     * @param onReady - the callback to call when the game is ready
     * @throws MqttException
     */
    public OnlineMatchImpl(final User user, final Runnable onReady) throws MqttException {
        this.localUser = user;
        this.onReady = onReady;
        this.network = new NetworkMatchManagerImpl(this::onMovement);
    }

    @Override
    public void setOnMovementHandler(final MovementHandler onMovementHandler) {
        this.onMovementHandler = onMovementHandler;
    }

    @Override
    public void exit() {
        this.network.disconnect();
    }

    @Override
    public void join(final String matchID) {
        // For now the player which join is the black player.
        this.localPlayer = new PlayerImpl(PlayerColor.BLACK, this.localUser);
        this.matchID = matchID;
        this.network.joinMatch(matchID, this.localPlayer, this::onDataReceived);
    }

    @Override
    public String create(final GameType gameType) {
        // For now the player which create is the white player.
        this.localPlayer = new PlayerImpl(PlayerColor.WHITE, this.localUser);
        this.data = new NetworkMatchData(gameType, this.timer, this.localPlayer);
        this.matchID = this.network.createMatch(this.data, this::onUserJoined);
        return this.matchID;
    }

    @Override
    public boolean isWhitePlayer() {
        return this.localPlayer.getColor().equals(PlayerColor.WHITE);
    }

    private void onDataReceived() {
        this.data = this.network.getMatchData();
        this.otherPlayer = this.data.getPlayer();
        final PlayerPair players = new PlayerPairImpl(this.otherPlayer, this.localPlayer);
        this.match = new MatchBuilderImpl().game(this.data.getGameType().getGameInstance(players))
                .timer(this.data.getTimer().getTimer(players)).build();

        Optional.ofNullable(this.onReady).ifPresent(Runnable::run);
    }

    private void onUserJoined() {
        this.otherPlayer = this.network.getJoinedPlayer();
        final PlayerPair players = new PlayerPairImpl(this.localPlayer, this.otherPlayer);
        this.match = new MatchBuilderImpl().game(this.data.getGameType().getGameInstance(players))
                .timer(this.data.getTimer().getTimer(players)).build();
        Optional.ofNullable(this.onReady).ifPresent(Runnable::run);
    }

    private void onMovement(final NetworkMovement movement) {
        final PieceMovement realMovement = new PieceMovementImpl(
                this.getBoard().getPieceAtPosition(movement.getOrigin()).get(), movement.getDestination());
        final MovementResult res = this.match.move(realMovement);
        if (!res.equals(MovementResult.INVALID_MOVE)) {
            this.onMovementHandler.handleMovement(realMovement, res);
        }

    }

    @Override
    public MovementResult move(final PieceMovement movement) {
        final MovementResult res = this.match.move(movement);
        if (!res.equals(MovementResult.INVALID_MOVE)) {
            this.network.sendMove(movement);
        }
        return res;
    }

    @Override
    public String getMatchID() {
        return this.matchID;
    }

    @Override
    public PlayerPair getPlayers() {
        return this.match.getPlayers();
    }

    @Override
    public Game getGame() {
        return this.match.getGame();
    }

    @Override
    public Timer getTimer() {
        return this.match.getTimer();
    }

    @Override
    public History getHistory() {
        return this.match.getHistory();
    }

    @Override
    public Board getBoard() {
        return this.match.getBoard();
    }

    @Override
    public void start() {
        this.match.start();
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
    public MatchStatus getMatchStatus() {
        return this.match.getMatchStatus();
    }

    @Override
    public Optional<MatchEndType> getEndType() {
        return this.match.getEndType();
    }

    @Override
    public void resign(final Player player) {
        // NO RESIGN IN ONLINE, FOR NOW
    }

}
