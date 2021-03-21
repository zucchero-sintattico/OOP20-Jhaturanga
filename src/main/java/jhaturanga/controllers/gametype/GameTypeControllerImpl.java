package jhaturanga.controllers.gametype;

import java.util.Arrays;
import java.util.List;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.DefaultsTimers;

public final class GameTypeControllerImpl extends AbstractController implements GameTypeController {

    @Override
    public int getNumberOfRow() {
        return getNumberOfGameTypes() / 2;
    }

    @Override
    public int getNumberOfColumn() {
        return getNumberOfGameTypes() / 2;
    }

    @Override
    public int getNumberOfGameTypes() {
        return GameTypesEnum.values().length;
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
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

    @Override
    public List<DefaultsTimers> getTimers() {
        return Arrays.asList(DefaultsTimers.values());
    }

}
