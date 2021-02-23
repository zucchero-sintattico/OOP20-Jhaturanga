package jhaturanga.controllers.gametypemenu;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameType;

public interface GameTypeMenuController extends Controller {

    int getNumberOfRow();

    int getNumberOfColumn();

    int getnNumbersOfGameTipes();

    void setGameType(GameType gameType);
}
