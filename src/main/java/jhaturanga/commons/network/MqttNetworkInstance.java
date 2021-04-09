package jhaturanga.commons.network;

import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public interface MqttNetworkInstance {

    /**
     * Connect to the network.
     */
    void connect() throws MqttSecurityException, MqttException;

    /**
     * 
     */
    void disconnect() throws MqttException;

    /**
     * Subscribe to a topic.
     * 
     * @param topic - the topic to subscribe
     */
    void subscribe(String topic) throws MqttException;

    /**
     * Send a DATA message.
     * 
     * @param topic - the topic
     * @param data
     */
    void sendData(String topic, String data) throws MqttPersistenceException, MqttException;

    /**
     * Send a JOIN message.
     * 
     * @param topic - the topic
     * @param data
     */
    void sendJoin(String topic, String data) throws MqttPersistenceException, MqttException;

    /**
     * Send a MOVE message.
     * 
     * @param topic - the topic
     * @param data
     */
    void sendMove(String topic, String data) throws MqttPersistenceException, MqttException;

    /**
     * 
     * @param topic
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    void sendResign(String topic) throws MqttPersistenceException, MqttException;

    /**
     * Set a callback for handling receiving of a message.
     * 
     * @param callback - the callback function
     */
    void setOnReceive(Consumer<NetworkMessage> callback);

}
