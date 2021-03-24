package jhaturanga.controllers.home;

import java.util.Optional;

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
     * Returns the name of the setted gametype if present.
     * 
     * @return String - The GameType's name.
     */
    Optional<String> getGameTypeName();

    /**
     * 
     * @return true if the DynamicGameType is present.
     */
    boolean isDynamicGameTypePresent();

    /**
     * Call it when match is about to be created and players need to be created.
     */
    void setupPlayers();

    /**
     * Call it when match is about to be created but the chess problem needs to be
     * setup.
     */
    void setupChessProblemAndCrateMatch();

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
     * Returns the first User if logged.
     * 
     * @return fist User logged
     */
    Optional<User> getFirstUser();

    /**
     * Returns the second User if logged.
     * 
     * @return second User logged
     */
    Optional<User> getSecondUser();

}
