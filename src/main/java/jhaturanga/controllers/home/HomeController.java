package jhaturanga.controllers.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
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
     * Set the timer.
     * 
     * @param timer
     */
    void setTimer(Timer timer);

    /**
     * Create the match.
     * 
     */
    void createMatch();

    /**
     * 
     * @return name of selected game type.
     */
    Optional<String> getNameGameTypeSelected();

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

    /**
     * 
     * @return List containing board
     * @throws IOException
     * @throws ClassNotFoundException
     */
    List<Board> loadMatch() throws IOException, ClassNotFoundException;

}
