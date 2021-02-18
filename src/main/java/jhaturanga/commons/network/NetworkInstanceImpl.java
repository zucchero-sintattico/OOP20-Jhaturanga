package jhaturanga.commons.network;

import java.util.Random;
import java.util.function.BiConsumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public final class NetworkInstanceImpl implements NetworkInstance {

    private static final int CLIENT_ID_LENGTH = 30;

    private final MqttClient client;
    private final String clientId;

    public NetworkInstanceImpl(final String broker) throws MqttException {
        this.clientId = this.generateRandomString(CLIENT_ID_LENGTH);
        this.client = new MqttClient(broker, this.clientId, null);
    }

    @Override
    public void connect() throws MqttSecurityException, MqttException {

        final MqttConnectOptions connOpts = new MqttConnectOptions();

        connOpts.setCleanSession(true); // no persistent session
        // connOpts.setKeepAliveInterval(1000);

        this.client.connect(connOpts);
    }

    @Override
    public void subscribe(final String topic) throws MqttException {

        this.client.subscribe(topic);

    }

    @Override
    public void send(final String topic, final String message) throws MqttPersistenceException, MqttException {

        final NetworkMessage networkMessage = new NetworkMessage(this.clientId, message);

        final MqttMessage mqttMessage = new MqttMessage(networkMessage.export().getBytes());

        mqttMessage.setQos(1); // sets qos level 1
        mqttMessage.setRetained(true); // sets retained message

        this.client.getTopic(topic).publish(mqttMessage);
    }

    @Override
    public void setOnReceive(final BiConsumer<String, MqttMessage> callback) {
        this.client.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(final String topic, final MqttMessage message) throws Exception {

                final NetworkMessage networkMessage = new NetworkMessage(message.toString());
                if (!networkMessage.getSenderId().equals(clientId)) {
                    callback.accept(topic, message);
                }

            }

            @Override
            public void deliveryComplete(final IMqttDeliveryToken token) {

            }

            @Override
            public void connectionLost(final Throwable cause) {

            }
        });
    }

    private String generateRandomString(final int length) {
        final String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String asciiLowerCase = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "1234567890";
        final String asciiChars = asciiUpperCase + asciiLowerCase + digits;

        final StringBuilder sb = new StringBuilder();

        int i = 0;
        final Random rand = new Random();
        while (i < length) {
            sb.append(asciiChars.charAt(rand.nextInt(asciiChars.length())));
            i++;
        }
        return sb.toString();
    }

}
