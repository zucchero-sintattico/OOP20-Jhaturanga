package jhaturanga.controllers.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
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

//TODO: TOMMASO DOCUMENTA!

    Optional<String> getNameGameTypeSelected();

    void setWhitePlayer(Player player);

    void setBlackPlayer(Player player);

    boolean isFirstUserLogged();

    boolean isSecondUserLogged();

    void setFirstUserGuest();

    void setSecondUserGuest();

    User getFirstUser();

    User getSecondUser();

    List<Board> loadMatch() throws IOException, ClassNotFoundException;

}
