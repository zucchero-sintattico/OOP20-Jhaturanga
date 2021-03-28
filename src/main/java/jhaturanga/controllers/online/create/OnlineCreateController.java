package jhaturanga.controllers.online.create;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public interface OnlineCreateController extends Controller {

    /**
     * 
     * @param gameType - the game type
     */
    void setGameType(GameTypesEnum gameType);

    /**
     * 
     * @return the game type
     */
    Optional<GameTypesEnum> getSelectedGameType();

    /**
     * 
     * @param onReady
     * @return the match id
     */
    String createMatch(Runnable onReady);
}
