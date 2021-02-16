package jhaturanga.controllers.home;

import java.util.List;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

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
     * Set the match players.
     * 
     * @param players
     */
    void setPlayers(List<Player> players);

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
}
