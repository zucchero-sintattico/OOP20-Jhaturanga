package jhaturanga.controllers.setup;

import java.util.Optional;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.controllers.Controller;
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
     * Get the selected game type.
     * 
     * @return the selected game type
     */
    Optional<GameTypesEnum> getSelectedGameType();

    /**
     * Get the selected timer.
     * 
     * @return the selected timer.
     */
    Optional<DefaultTimers> getSelectedTimer();

    /**
     * Get the selected white player choice mode.
     * 
     * @return the selected white player choice mode.
     */
    Optional<WhitePlayerChoice> getSelectedWhitePlayerChoice();

    /**
     * Create the match.
     * 
     * @return true if the match was created, false otherwise
     */
    boolean createMatch();
}
