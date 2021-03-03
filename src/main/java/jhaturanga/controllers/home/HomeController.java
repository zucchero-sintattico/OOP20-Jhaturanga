package jhaturanga.controllers.home;

import java.util.List;
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
    String getUserNameLoggedUsers();

    int getNumbersOfLoggedUser();

    Optional<String> getNameGameTypeSelected();

    void setWhitePlayer(Player player);

    void setBlackPlayer(Player player);

    List<User> getUsers();

    List<Player> getPlayer();

    void addUser(User user);
}
