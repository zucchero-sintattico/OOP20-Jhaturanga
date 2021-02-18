package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public final class Network {

    private Network() {

    }

    public static void main(final String[] args) throws MqttException, InterruptedException {

        final String broker = "tcp://test.mosquitto.org:1883";
        final String clientId = String.valueOf(System.nanoTime());
        final String topicName = "test/jhaturanga";
        final int qos = 1;

        final MqttClient mqttClient = new MqttClient(broker, clientId, null);
        // Mqtt ConnectOptions is used to set the additional features to mqtt message

        final MqttConnectOptions connOpts = new MqttConnectOptions();

        connOpts.setCleanSession(true); // no persistent session
        connOpts.setKeepAliveInterval(1000);

        final MqttMessage message = new MqttMessage("Hello World from Jhaturanga".getBytes());

        message.setQos(qos); // sets qos level 1
        message.setRetained(true); // sets retained message

        final MqttTopic topic2 = mqttClient.getTopic(topicName);

        mqttClient.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                System.out.println("Message arrived - topic: " + topic + " - message: " + message);
            }

            @Override
            public void deliveryComplete(final IMqttDeliveryToken token) {
                System.out.println("Delivery Complete : " + token.toString());
            }

            @Override
            public void connectionLost(final Throwable cause) {
                System.out.println("Connection Lost : " + cause.toString());

            }

        });

        mqttClient.connect(connOpts); // connects the broker with connect options

        mqttClient.subscribe(topicName);

        while (true) {
            topic2.publish(message); // publishes the message to the topic(test/topic)
            Thread.sleep(1000);
        }

//        // We're using eclipse paho library so we've to go with MqttCallback
//        final MqttClient client = new MqttClient(broker, clientId);
//
//        client.setCallback(new MqttCallback() {
//
//            @Override
//            public void messageArrived(final String topic, final MqttMessage message) throws Exception {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void deliveryComplete(final IMqttDeliveryToken token) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void connectionLost(final Throwable cause) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        final MqttConnectOptions mqOptions = new MqttConnectOptions();
//
//        mqOptions.setCleanSession(true);
//        client.connect(mqOptions); // connecting to broker
//        client.subscribe("test/topic"); // subscribing to the topic name test/topic

    }

}
