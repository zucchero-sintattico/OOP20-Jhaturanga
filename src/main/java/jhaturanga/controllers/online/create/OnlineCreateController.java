package jhaturanga.controllers.online.create;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.type.GameType;

/**
 * The controller for the online match creation page view.
 *
 */
public interface OnlineCreateController extends Controller {

    /**
     * Set the game type.
     * 
     * @param gameType - the game type
     */
    void setGameType(GameType gameType);

    /**
     * 
     * @param onReady
     * @return the match id
     * @throws MqttException
     */
    String createMatch(Runnable onReady) throws MqttException;
}
