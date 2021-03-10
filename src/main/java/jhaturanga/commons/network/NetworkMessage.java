package jhaturanga.commons.network;

import java.io.IOException;

/**
 * A packet to send over MQTT.
 *
 */
public final class NetworkMessage {

    private String senderId;
    private NetworkMessageType type;
    private String content;

    /**
     * Create an empty message.
     */
    public NetworkMessage() {

    }

    /**
     * Create a Network Message with senderId and content.
     * 
     * @param senderId - the id of the sender
     * @param type     - the type of the message
     * @param content  - the content of the message
     */
    public NetworkMessage(final String senderId, final NetworkMessageType type, final String content) {
        this.senderId = senderId;
        this.type = type;
        this.content = content;
    }

    public static NetworkMessage join(final String senderId, final String username) {
        return new NetworkMessage(senderId, NetworkMessageType.JOIN, username);
    }

    public static NetworkMessage data(final String senderId, final String data) {
        return new NetworkMessage(senderId, NetworkMessageType.DATA, data);
    }

    /**
     * Create a Network Message from an export network message.
     * 
     * @param message - the exported message
     */
    public NetworkMessage(final String message) {
        this.fromExported(message);
    }

    public String export() {
        try {
            return this.senderId + ":" + ObjectSerializer.toString(this.type) + ":" + this.content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fromExported(final String packet) {
        final String[] args = packet.split(":");
        this.senderId = args[0];
        try {
            this.type = (NetworkMessageType) ObjectSerializer.fromString(args[1]);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        this.content = args[2];
    }

    /**
     * Get the id of the sender.
     * 
     * @return the sender of this message
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Get the message type.
     * 
     * @return the message type
     */
    public NetworkMessageType getMessageType() {
        return this.type;
    }

    /**
     * Get the content of this message.
     * 
     * @return the content of this message
     */
    public String getContent() {
        return content;
    }
}
