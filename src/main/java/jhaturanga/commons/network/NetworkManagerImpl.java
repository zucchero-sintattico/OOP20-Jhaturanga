package jhaturanga.commons.network;

import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import jhaturanga.model.movement.Movement;

public final class NetworkManagerImpl implements NetworkManager {

    private static final int MATCH_ID_LENGHT = 6;

    private final String broker = "tcp://test.mosquitto.org:1883";
    private final String clientId;
    private final MqttClient client;

    private String actualMatchId;
    private MqttTopic actualMatchTopic;

    public NetworkManagerImpl(final Consumer<String> onReceive) throws MqttException {
        this.clientId = String.valueOf(System.nanoTime());
        this.client = new MqttClient(this.broker, this.clientId, null);

        final MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true); // no persistent session
        connOpts.setKeepAliveInterval(1000);

        this.client.connect(connOpts); // connects the broker with connect options

        this.client.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                onReceive.accept(message.getPayload().toString());
            }

            @Override
            public void deliveryComplete(final IMqttDeliveryToken token) {
                // TODO Auto-generated method stub

            }

            @Override
            public void connectionLost(final Throwable cause) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void sendMove(final Movement move) {
        final MqttMessage message = new MqttMessage(move.toString().getBytes());

        message.setQos(1); // sets qos level 1
        message.setRetained(true); // sets retained message

        try {
            this.actualMatchTopic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createGame() {
        return generateRandomString(MATCH_ID_LENGHT);
    }

    @Override
    public boolean joinMatch(final String matchId) {
        this.actualMatchId = matchId;
        this.actualMatchTopic = this.client.getTopic(matchId);
        try {
            this.client.subscribe(matchId);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static String generateRandomString(final int length) {
        // final String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // final String asciiLowerCase = asciiUpperCase.toLowerCase();
        // final String asciiChars = asciiUpperCase + asciiLowerCase + digits;

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
