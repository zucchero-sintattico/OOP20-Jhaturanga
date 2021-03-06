package jhaturanga.controllers.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.gametypes.GameTypesEnum;
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
     * @return the match
     */
    Match createMatch();

//TODO: TOMMASO DOCUMENTA
    List<String> getUserNameLoggedUsers();

    int getNumbersOfLoggedUser();

    Optional<String> getNameGameTypeSelected();

    void setWhitePlayer(Player player);

    void setBlackPlayer(Player player);

    List<User> getUsers();

    List<Player> getPlayer();

    void addUser(User user);

    List<Board> loadMatch() throws IOException, ClassNotFoundException;

    void logOut();
}
