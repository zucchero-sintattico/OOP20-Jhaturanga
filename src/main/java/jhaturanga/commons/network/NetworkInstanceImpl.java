package jhaturanga.commons.network;

import java.util.Random;
import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public final class NetworkInstanceImpl implements NetworkInstance {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final int CLIENT_ID_LENGTH = 30;

    private final MqttClient client;
    private final String clientId;

    public NetworkInstanceImpl() throws MqttException {
        this(BROKER);
    }

    public NetworkInstanceImpl(final String broker) throws MqttException {
        this.clientId = this.generateRandomString(CLIENT_ID_LENGTH);
        this.client = new MqttClient(broker, this.clientId, null);
    }

    @Override
    public void connect() throws MqttSecurityException, MqttException {

        final MqttConnectOptions connOpts = new MqttConnectOptions();
        final int keepAliveInterval = 600_000;

        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(false); // no persistent session
        connOpts.setKeepAliveInterval(keepAliveInterval);

        this.client.connect(connOpts);

    }

    @Override
    public void disconnect() throws MqttException {
        this.client.disconnect();
    }

    @Override
    public void subscribe(final String topic) throws MqttException {
        this.client.subscribe(topic);
    }

    private void send(final String topic, final NetworkMessageType type, final String message)
            throws MqttPersistenceException, MqttException {

        final NetworkMessage networkMessage = new NetworkMessage(this.clientId, type, message);

        final MqttMessage mqttMessage = new MqttMessage(networkMessage.export().getBytes());

        mqttMessage.setQos(1); // sets qos level 1
        mqttMessage.setRetained(true); // sets retained message

        this.client.getTopic(topic).publish(mqttMessage);
    }

    @Override
    public void setOnReceive(final Consumer<NetworkMessage> callback) {
        this.client.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(final String topic, final MqttMessage message) throws Exception {

                final NetworkMessage networkMessage = new NetworkMessage(message.toString());
                if (!networkMessage.getSenderId().equals(clientId)) {
                    callback.accept(networkMessage);
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

    @Override
    public void sendData(final String topic, final String data) throws MqttPersistenceException, MqttException {
        System.out.println("SEND DATA");
        this.send(topic, NetworkMessageType.DATA, data);
    }

    @Override
    public void sendJoin(final String topic, final String data) throws MqttPersistenceException, MqttException {
        System.out.println("SEND JOIN");
        this.send(topic, NetworkMessageType.JOIN, data);
    }

    @Override
    public void sendMove(final String topic, final String data) throws MqttPersistenceException, MqttException {
        System.out.println("SEND MOVE");
        this.send(topic, NetworkMessageType.MOVE, data);
    }

    @Override
    public void sendResign(final String topic) throws MqttPersistenceException, MqttException {
        System.out.println("SEND RESIGN");
        this.send(topic, NetworkMessageType.RESIGN, "RESIGN");
    }

}
