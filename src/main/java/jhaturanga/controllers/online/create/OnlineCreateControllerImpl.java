package jhaturanga.controllers.online.create;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;

public final class OnlineCreateControllerImpl extends AbstractController implements OnlineCreateController {

    private GameTypesEnum gameType;

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.gameType = gameType;
    }

    @Override
    public Optional<GameTypesEnum> getSelectedGameType() {
        return Optional.ofNullable(this.gameType);
    }

    @Override
    public String createMatch(final Runnable onReady) {
        if (this.gameType == null) {
            return null;
        }

        final NetworkMatch match = new NetworkMatch(this.getApplicationInstance().getFirstUser().get(), onReady);

        this.getApplicationInstance().setMatch(match);
        return match.create(this.getSelectedGameType().get());
    }

}
