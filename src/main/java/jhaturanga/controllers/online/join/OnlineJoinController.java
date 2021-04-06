package jhaturanga.controllers.online.join;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.Controller;

public interface OnlineJoinController extends Controller {

    /**
     * 
     * @param matchID
     * @param onReady
     * @throws MqttException
     */
    void join(String matchID, Runnable onReady) throws MqttException;
}
