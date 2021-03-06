package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
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
    void setGameType(GameType gameType);

    /**
     * Set the timer.
     * 
     * @param timer
     */
    void setTimer(Timer timer);

    /**
     * Create the match.
     * 
     * @return the match
     */
    Match createMatch();

//TODO: TOMMASO DOCUMENTA

    Optional<String> getNameGameTypeSelected();

    void setWhitePlayer(Player player);

    void setBlackPlayer(Player player);

    boolean isFirstUserLogged();

    boolean isSecondUserLogged();

    void setFirstUserGuest();

    void setSecondUserGuest();

    User getFirstUser();

    User getSecondUser();

}
