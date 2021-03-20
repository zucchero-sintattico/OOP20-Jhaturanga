package jhaturanga.controllers.gametypemenu;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;

public interface GameTypeController extends Controller {

    /**
     * get white player.
     * 
     * @return white player.
     */
    Player getWhitePlayer();

    /**
     * get black player.
     * 
     * @return black player
     */
    Player getBlackPlayer();

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
