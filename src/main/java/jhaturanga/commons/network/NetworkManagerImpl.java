package jhaturanga.commons.network;

import java.io.IOException;
import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.movement.Movement;

public final class NetworkManagerImpl implements NetworkManager {

    private static final int MATCH_ID_LENGTH = 5;
    private static final String GAME_CHANNEL_BASE = "jhaturanga/game/";

    private final NetworkInstance network;
    private final Consumer<NetworkMovement> onMovement;

    private String gameUrl;

    private NetworkMatchData matchData;
    private String joinedPlayerUserName = "";

    public NetworkManagerImpl(final Consumer<NetworkMovement> onMovement) throws MqttException {
        this.network = new NetworkInstanceImpl();
        this.network.connect();
        this.onMovement = onMovement;
        this.network.setOnReceive(this::onMessage);
    }

    private void onMessage(final String topic, final NetworkMessage message) {

        switch (message.getMessageType()) {
        case JOIN:
            System.out.println("JOIN from : " + message.getSenderId());
            break;
        case DATA:
            break;
        case MOVE:
            try {
                this.onMovement.accept((NetworkMovement) ObjectSerializer.fromString(message.getContent()));
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }

    }

    @Override
    public String createMatch(final NetworkMatchData data) {
        // Generate a random string
        this.matchData = data;
        final String matchId = this.idGenerator(MATCH_ID_LENGTH);
        this.joinMatch(matchId);
        return matchId;
    }

    @Override
    public void joinMatch(final String matchId) {
        this.gameUrl = GAME_CHANNEL_BASE + matchId;

        try {
            this.network.send(this.gameUrl, NetworkMessageType.JOIN, "JOIN");
            this.network.subscribe(this.gameUrl);
        } catch (final MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("PMD.IdenticalCatchBranches")
    public void sendMove(final Movement move) {
        try {
            final NetworkMovement movement = new NetworkMovement(move.getOrigin(), move.getDestination());
            this.network.send(this.gameUrl, NetworkMessageType.MOVE, ObjectSerializer.toString(movement));
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
    public String getJoinedUserName() {
        return null;
    }

}
