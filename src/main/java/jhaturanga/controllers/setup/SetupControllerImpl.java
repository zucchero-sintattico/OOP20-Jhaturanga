package jhaturanga.controllers.setup;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public final class SetupControllerImpl extends AbstractController implements SetupController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

}
