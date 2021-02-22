package jhaturanga.controllers.gametypemenu;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;

public final class GameTypeControllerImpl extends AbstractController implements GameTypeMenuController {

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
    public void setGameType(final GameType gameType) {
        this.getModel().setGameType(gameType);
    }

    @Override
    public Player getWhitePlayer() {
        return this.getModel().getWhitePlayer();
    }

    @Override
    public Player getBlackPlayer() {
        return this.getModel().getBlackPlayer();
    }

}
