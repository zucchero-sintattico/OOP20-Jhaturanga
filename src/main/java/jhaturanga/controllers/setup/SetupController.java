package jhaturanga.controllers.setup;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultTimers;

public interface SetupController extends Controller {

    /**
     * Set the game type.
     * 
     * @param gameType - the game type to be setted
     */
    void setGameType(GameTypesEnum gameType);

    /**
     * Set the timer for the game.
     * 
     * @param timer - the timer to be setted
     */
    void setTimer(DefaultTimers timer);

    /**
     * Set the white player choosing mode.
     * 
     * @param choice - the white player choosing mode.
     */
    void setWhitePlayerChoice(WhitePlayerChoice choice);

    /**
     * Create the match.
     * 
     * @return true if the match was created, false otherwise
     */
    boolean createMatch();
}
