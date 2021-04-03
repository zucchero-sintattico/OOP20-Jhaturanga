package jhaturanga.controllers.online.create;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.type.GameType;

public interface OnlineCreateController extends Controller {

    /**
     * 
     * @param gameType - the game type
     */
    void setGameType(GameType gameType);

    /**
     * 
     * @return the game type
     */
    Optional<GameType> getSelectedGameType();

    /**
     * 
     * @param onReady
     * @return the match id
     */
    String createMatch(Runnable onReady);
}
