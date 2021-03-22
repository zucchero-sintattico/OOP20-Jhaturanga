package jhaturanga.controllers.gametype;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public final class GameTypeControllerImpl extends AbstractController implements GameTypeController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

}
