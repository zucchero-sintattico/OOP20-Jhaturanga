package jhaturanga.commons.network;

import java.util.function.BiConsumer;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public interface NetworkInstance {

    /**
     * Connect to the network.
     */
    void connect() throws MqttSecurityException, MqttException;

    /**
     * Subscribe to a topic.
     * 
     * @param topic - the topic to subscribe
     */
    void subscribe(String topic) throws MqttException;

    /**
     * Send a message.
     * 
     * @param topic   - the topic where to send the message
     * @param message - the message to send
     */
    void send(String topic, String message) throws MqttPersistenceException, MqttException;

    /**
     * Set a callback for handling receiving of a message.
     * 
     * @param callback - the callback function
     */
    void setOnReceive(BiConsumer<String, MqttMessage> callback);

}
