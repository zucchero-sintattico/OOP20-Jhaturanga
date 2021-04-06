package jhaturanga.controllers.online.create;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.online.OnlineMatchImpl;

public final class OnlineCreateControllerImpl extends BasicController implements OnlineCreateController {

    private GameType gameType;

    @Override
    public void setGameType(final GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public Optional<GameType> getSelectedGameType() {
        return Optional.ofNullable(this.gameType);
    }

    @Override
    public String createMatch(final Runnable onReady) throws MqttException {
        if (this.gameType == null) {
            return null;
        }

        final OnlineMatchImpl match = new OnlineMatchImpl(this.getApplicationInstance().getFirstUser().get(), onReady);

        this.getApplicationInstance().setMatch(match);
        return match.create(this.getSelectedGameType().get());
    }

}
