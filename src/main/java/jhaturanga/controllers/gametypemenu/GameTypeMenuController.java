package jhaturanga.controllers.gametypemenu;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;

public interface GameTypeMenuController extends Controller {

    /**
     * get white player if present.
     * 
     * @return white player.
     */
    Optional<Player> getWhitePlayer();

    /**
     * get black player if present.
     * 
     * @return black player
     */
    Optional<Player> getBlackPlayer();

    /**
     * get number of row of the grid in the view.
     * 
     * @return number of rows of tabs in GameTypeMenu grid.
     */
    int getNumberOfRow();

    /**
     * get number of columns of the grid in the view.
     * 
     * @return number of column of tabs in GameTypeMenu grid.
     */
    int getNumberOfColumn();

    /**
     * 
     * @return numbers of game tips;
     */
    int getNumberOfGameTypes();

    /**
     * 
     * @param gameType
     */
    void setGameType(GameTypesEnum gameType);
}
