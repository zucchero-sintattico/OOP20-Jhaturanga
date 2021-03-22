package jhaturanga.controllers.setup;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public interface SetupController extends Controller {

    /**
     * 
     * @param gameType
     */
    void setGameType(GameTypesEnum gameType);
}
