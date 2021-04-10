package jhaturanga.commons.network;

import java.io.IOException;

import jhaturanga.commons.ObjectSerializer;

/**
 * A packet to send over MQTT.
 * 
 * Contains: - senderId - message type - message content.
 */
public final class NetworkMessage {

    private String senderId;
    private NetworkMessageType type;
    private String content;

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

    /**
     * Create a join message.
     * 
     * @param senderId - the sender ID
     * @param message  - the message
     * @return the network message for JOIN packet
     */
    public static NetworkMessage join(final String senderId, final String message) {
        return new NetworkMessage(senderId, NetworkMessageType.JOIN, message);
    }

    /**
     * Create a DATA message.
     * 
     * @param senderId - the sender ID
     * @param data     - the message
     * @return the network message for DATA packet
     */
    public static NetworkMessage data(final String senderId, final String data) {
        return new NetworkMessage(senderId, NetworkMessageType.DATA, data);
    }

    /**
     * Create a MOVE message.
     * 
     * @param senderId - the sender ID
     * @param move     - the movement
     * @return the network message for MOVE packet
     */
    public static NetworkMessage move(final String senderId, final String move) {
        return new NetworkMessage(senderId, NetworkMessageType.MOVE, move);
    }

    public static NetworkMessage resign(final String senderId) {
        return new NetworkMessage(senderId, NetworkMessageType.RESIGN, null);
    }

    /**
     * Create a Network Message from an export network message.
     * 
     * @param message - the exported message
     */
    public NetworkMessage(final String message) {
        this.deserialize(message);
    }

    /**
     * Export the network message to a string.
     * 
     * @return the exported network message
     */
    public String serialize() {
        try {
            return this.senderId + ":" + ObjectSerializer.toString(this.type) + ":" + this.content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Import a network message.
     * 
     * @param serializedNetworkMessage - the exported network message
     */
    public void deserialize(final String serializedNetworkMessage) {
        final String[] args = serializedNetworkMessage.split(":");
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
