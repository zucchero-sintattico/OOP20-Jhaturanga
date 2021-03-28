package jhaturanga.commons.network;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.movement.Movement;
import jhaturanga.model.player.Player;

public final class NetworkMatchManagerImpl implements NetworkMatchManager {

    private static final int MATCH_ID_LENGTH = 3;
    private static final String GAME_CHANNEL_BASE = "jhaturanga/game/";

    private final NetworkInstance network;
    private final Consumer<NetworkMovement> onMovement;
    private Runnable onReady;

    private String gameUrl = "";

    private NetworkMatchData matchData;
    private Player joinedPlayer;

    public NetworkMatchManagerImpl(final Consumer<NetworkMovement> onMovement) throws MqttException {
        this.network = new NetworkInstanceImpl();
        this.network.connect();
        this.onMovement = onMovement;
        this.network.setOnReceive(this::onMessage);
    }

    private void handleJoinMessage(final NetworkMessage message) {
        /**
         * A user joined to the game that I created.
         * 
         * I send him this game settings.
         */
        System.out.println("A user joined.");
        try {

            // Load the player
            this.joinedPlayer = (Player) ObjectSerializer.fromString(message.getContent());

            // Send the game data to the player who joined
            this.sendGameDataToJoinedUser();

            // Now I'm ready to start so i call onReady callback
            Optional.ofNullable(this.onReady).ifPresent(Runnable::run);

        } catch (ClassNotFoundException | IOException e1) {
            e1.printStackTrace();
        }
    }

    private void handleDataMessage(final NetworkMessage message) {
        /**
         * A user send me the data of the game, so i'm the player who joined.
         */
        System.out.println("The host send the game data.");
        try {

            // Load the match data
            this.matchData = (NetworkMatchData) ObjectSerializer.fromString(message.getContent());

            // Now I'm ready to start so i call onReady callback
            this.onReady.run();

        } catch (ClassNotFoundException | IOException e1) {
            e1.printStackTrace();
        }
    }

    private void handleMoveMessage(final NetworkMessage message) {

        /**
         * The other player make a move.
         */
        System.out.println("A player sent a move.");
        try {

            // Call the callback of the onMovement
            this.onMovement.accept((NetworkMovement) ObjectSerializer.fromString(message.getContent()));

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void onMessage(final NetworkMessage message) {
        switch (message.getMessageType()) {

        case JOIN:
            this.handleJoinMessage(message);
            break;
        case DATA:
            this.handleDataMessage(message);
            break;
        case MOVE:
            this.handleMoveMessage(message);
            break;
        default:
            break;
        }

    }

    private void sendGameDataToJoinedUser() {
        try {
            /**
             * Send the game data to the player who joined.
             */
            this.network.sendData(this.gameUrl, ObjectSerializer.toString(this.matchData));
        } catch (MqttException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createMatch(final NetworkMatchData data, final Runnable onReady) {

        // Setup the match data
        this.matchData = data;

        // Generate an id for the match
        final String matchId = this.idGenerator(MATCH_ID_LENGTH);

        // Join this match
        this.joinMatch(matchId, data.getPlayer(), onReady);

        // Return the game ID
        return matchId;
    }

    @Override
    public void joinMatch(final String matchId, final Player player, final Runnable onReady) {

        // Setup the callback
        this.onReady = onReady;

        this.gameUrl = GAME_CHANNEL_BASE + matchId;

        try {

            /**
             * Send a join message and subscribe.
             */
            this.network.sendJoin(this.gameUrl, ObjectSerializer.toString(player));
            this.network.subscribe(this.gameUrl);

        } catch (final MqttException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMove(final Movement move) {
        try {

            /**
             * Send a movement.
             */
            final NetworkMovement movement = new NetworkMovement(move.getOrigin(), move.getDestination());
            this.network.sendMove(this.gameUrl, ObjectSerializer.toString(movement));

        } catch (final MqttException | IOException e) {
            e.printStackTrace();
        }
    }

    private String idGenerator(final int length) {
        final String digits = "1234567890";
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        final Random rand = new Random();
        while (i < length) {
            sb.append(digits.charAt(rand.nextInt(digits.length())));
            i++;
        }
        return sb.toString();
    }

    @Override
    public NetworkMatchData getMatchData() {
        return this.matchData;
    }

    @Override
    public Player getJoinedPlayer() {
        return this.joinedPlayer;
    }

    @Override
    public void disconnect() {
        try {
            this.network.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
