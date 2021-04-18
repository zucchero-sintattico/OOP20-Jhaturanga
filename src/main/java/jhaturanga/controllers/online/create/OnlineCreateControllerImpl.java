package jhaturanga.controllers.online.create;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.online.OnlineMatchImpl;

/**
 * Basic implementation of the OnlineCreateController.
 */
public final class OnlineCreateControllerImpl extends BasicController implements OnlineCreateController {

    private GameType gameType;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameType(final GameType gameType) {
        this.gameType = gameType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createMatch(final Runnable onReady) throws MqttException {
        if (this.gameType == null) {
            return null;
        }

        final OnlineMatchImpl match = new OnlineMatchImpl(this.getModel().getFirstUser().get(), onReady);

        this.getModel().setMatch(match);
        return match.create(this.gameType);
    }

}
