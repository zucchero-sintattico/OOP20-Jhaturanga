package jhaturanga.commons.network;

import java.io.IOException;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.movement.Movement;

public final class NetworkManagerImpl implements NetworkManager {

    private static final int MATCH_ID_LENGTH = 5;
    private static final String GAME_CHANNEL_BASE = "jhaturanga/game/";

    private final NetworkInstance network;

    private String gameUrl;

    public NetworkManagerImpl() throws MqttException {
        this.network = new NetworkInstanceImpl();
        this.network.connect();
        this.network.setOnReceive(this::onMessage);
    }

    private void onMessage(final String topic, final NetworkMessage message) {

        switch (message.getMessageType()) {
        case JOIN:
            System.out.println("JOIN from : " + message.getSenderId());
            break;
        case MOVE:
            try {
                final Movement move = (Movement) ToStringSample.fromString(message.getContent());
                System.out.println("Topic : " + topic + " - Message : " + move);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }

    }

    @Override
    public String createMatch() {
        // Generate a random string
        final String matchId = this.idGenerator(MATCH_ID_LENGTH);
        this.joinMatch(matchId);
        return matchId;
    }

    @Override
    public boolean joinMatch(final String matchId) {
        this.gameUrl = GAME_CHANNEL_BASE + matchId;

        try {
            this.network.send(this.gameUrl, NetworkMessageType.JOIN, "JOIN");
            this.network.subscribe(this.gameUrl);
        } catch (final MqttException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @SuppressWarnings("PMD.IdenticalCatchBranches")
    public void sendMove(final Movement move) {
        try {
            this.network.send(this.gameUrl, NetworkMessageType.MOVE, ToStringSample.toString(move));
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

}
