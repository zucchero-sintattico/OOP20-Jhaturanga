package jhaturanga.controllers.online.join;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.match.OnlineMatchImpl;

public final class OnlineJoinControllerImpl extends AbstractController implements OnlineJoinController {

    @Override
    public void join(final String matchID, final Runnable onReady) throws MqttException {
        final OnlineMatchImpl match = new OnlineMatchImpl(this.getApplicationInstance().getFirstUser().get(), onReady);
        match.join(matchID);
        this.getApplicationInstance().setMatch(match);
    }

}
