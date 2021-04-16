package jhaturanga.model.match.online.network;

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

public final class MosquittoMqttNetworkInstance implements MqttNetworkInstance {

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final int CLIENT_ID_LENGTH = 30;

    private final MqttClient client;
    private final String clientId;

    public MosquittoMqttNetworkInstance() throws MqttException {
        this(BROKER);
    }

    public MosquittoMqttNetworkInstance(final String broker) throws MqttException {
        this.clientId = this.generateRandomString(CLIENT_ID_LENGTH);
        this.client = new MqttClient(broker, this.clientId, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect() throws MqttSecurityException, MqttException {

        final MqttConnectOptions connOpts = new MqttConnectOptions();
        final int keepAliveInterval = 600_000;

        connOpts.setAutomaticReconnect(true);
        connOpts.setCleanSession(false);
        connOpts.setKeepAliveInterval(keepAliveInterval);

        this.client.connect(connOpts);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect() throws MqttException {
        this.client.disconnect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(final String topic) throws MqttException {
        this.client.subscribe(topic);
    }

    private void send(final String topic, final NetworkMessage message) throws MqttPersistenceException, MqttException {
        final MqttMessage mqttMessage = new MqttMessage(message.serialize().getBytes());
        mqttMessage.setQos(1);
        mqttMessage.setRetained(true);
        this.client.getTopic(topic).publish(mqttMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnReceive(final Consumer<NetworkMessage> callback) {
        this.client.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(final String topic, final MqttMessage message) throws Exception {

                final NetworkMessage networkMessage = new NetworkMessage(message.toString());

                // Handle only message from other clients
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(final String topic, final String data) throws MqttPersistenceException, MqttException {
        this.send(topic, NetworkMessage.data(this.clientId, data));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendJoin(final String topic, final String data) throws MqttPersistenceException, MqttException {
        this.send(topic, NetworkMessage.join(this.clientId, data));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMove(final String topic, final String data) throws MqttPersistenceException, MqttException {
        this.send(topic, NetworkMessage.move(this.clientId, data));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendResign(final String topic) throws MqttPersistenceException, MqttException {
        this.send(topic, NetworkMessage.resign(this.clientId));
    }

}
