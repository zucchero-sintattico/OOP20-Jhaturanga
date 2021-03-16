package jhaturanga.controllers.home;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.user.User;

/**
 * The controller for the home page.
 */
public interface HomeController extends Controller {

    /**
     * Set the game type of the match.
     * 
     * @param gameType
     */
    void setGameType(GameTypesEnum gameType);

    /**
     * Set the timer from the DefaultTimer.
     * 
     * @param timer
     */
    void setTimer(DefaultsTimers timer);

    /**
     * Create the match.
     * 
     */
    void createMatch();

    /**
     * 
     * @return true if the GameType is present.
     */
    boolean isGameTypePresent();

    /**
     * Call it when match is about to be created and players need to be created.
     */
    void setupPlayers();

    /**
     * Set white player.
     * 
     * @param player
     */
    void setWhitePlayer(Player player);

    /**
     * Set black player.
     * 
     * @param player
     */
    void setBlackPlayer(Player player);

    /**
     * first player logged.
     * 
     * @return true if fist player is logged, else false.
     */
    boolean isFirstUserLogged();

    /**
     * second player logged.
     * 
     * @return true if second player is second, else false.
     */
    boolean isSecondUserLogged();

    /**
     * set the fist player as GUEST.
     */
    void setFirstUserGuest();

    /**
     * set the second player as GUEST.
     */
    void setSecondUserGuest();

    /**
     * 
     * @return fist User logged
     */
    User getFirstUser();

    /**
     * 
     * @return second User logged
     */
    User getSecondUser();

}
