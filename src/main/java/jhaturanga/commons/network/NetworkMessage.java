package jhaturanga.commons.network;

/**
 * A packet to send over MQTT.
 *
 */
public final class NetworkMessage {

    private String senderId;
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
     * @param content  - the content of the message
     */
    public NetworkMessage(final String senderId, final String content) {
        this.senderId = senderId;
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
        return this.senderId + ":" + this.content;
    }

    public void fromExported(final String packet) {
        final String[] args = packet.split(":");
        this.senderId = args[0];
        this.content = args[1];
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
     * Get the content of this message.
     * 
     * @return the content of this message
     */
    public String getContent() {
        return content;
    }
}
