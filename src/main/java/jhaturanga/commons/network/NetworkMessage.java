package jhaturanga.commons.network;

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

    /**
     * Create a Network Message from an export network message.
     * 
     * @param message - the exported message
     */
    public NetworkMessage(final String message) {
        this.fromExported(message);
    }

    public String export() {
        return this.senderId + ":" + this.type + ":" + this.content;
    }

    public void fromExported(final String packet) {
        final String[] args = packet.split(":");
        this.senderId = args[0];
        this.type = NetworkMessageType.fromString(args[1]);
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
