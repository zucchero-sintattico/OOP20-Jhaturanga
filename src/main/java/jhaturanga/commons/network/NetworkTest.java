package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.commons.CommandLine;

public final class NetworkTest {

    private NetworkTest() {

    }

    public static void main(final String[] args) throws MqttException {
        final String broker = "tcp://test.mosquitto.org:1883";
        final String topic = "test/jhaturanga-communication";

        final NetworkInstance instance1 = new NetworkInstanceImpl(broker);
        final CommandLine console = new CommandLine();

        instance1.connect();

        instance1.subscribe(topic);

        instance1.setOnReceive((top, mes) -> System.out.println("Message Received : " + mes));

        while (true) {
            final String message = console.readLine("Insert message: ");
            instance1.send(topic, message);
        }

    }

}
