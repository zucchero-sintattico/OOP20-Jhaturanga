package jhaturanga.controllers.online.join;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.Controller;

/**
 * The controller for the online match join page view.
 *
 */
public interface OnlineJoinController extends Controller {

    /**
     * Join the online match with the specified matchID.
     * 
     * @param matchID - the id of the match to join
     * @param onReady - the callback that handle when the match is ready.
     * @throws MqttException
     */
    void join(String matchID, Runnable onReady) throws MqttException;
}
