package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.MqttException;

public final class Network {

    private Network() {

    }

    public static void main(final String[] args) throws MqttException, InterruptedException {

        final String broker = "tcp://test.mosquitto.org:1883";
        final String topic = "test/jhaturanga-communication";

        final NetworkInstance instance1 = new NetworkInstanceImpl(broker);

        final NetworkInstance instance2 = new NetworkInstanceImpl(broker);

        instance1.connect();
        instance2.connect();

        instance1.subscribe(topic);
        instance2.subscribe(topic);

        instance1.setOnReceive(
                (top, mes) -> System.out.println("Instance 1 -> Topic : " + topic + " - Message : " + mes));

        instance2.setOnReceive(
                (top, mes) -> System.out.println("Instance 2 -> Topic : " + topic + " - Message : " + mes));

        new Thread(() -> {
            while (true) {
                try {
                    instance1.send(topic, "Hello from Instance 1");
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    instance2.send(topic, "Hello from Instance 2");
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
