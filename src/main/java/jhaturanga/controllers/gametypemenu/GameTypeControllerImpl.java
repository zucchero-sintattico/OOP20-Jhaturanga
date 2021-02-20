package jhaturanga.controllers.gametypemenu;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public final class GameTypeControllerImpl extends AbstractController implements GameTypeMenuController {

    public GameTypeControllerImpl() {

    }

    @Override
    public int getNumberOfRow() {
        return getnNumbersOfGameTipes() / 2;
    }

    @Override
    public int getNumberOfColumn() {
        return getnNumbersOfGameTipes() / 2;
    }

    @Override
    public int getnNumbersOfGameTipes() {
        return GameTypesEnum.values().length;
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

}
