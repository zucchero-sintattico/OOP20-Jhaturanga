package jhaturanga.controllers.gametype;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public interface GameTypeController extends Controller {

    /**
     * 
     * @param gameType
     */
    void setGameType(GameTypesEnum gameType);
}
