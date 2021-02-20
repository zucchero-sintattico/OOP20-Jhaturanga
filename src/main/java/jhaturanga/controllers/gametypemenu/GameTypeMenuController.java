package jhaturanga.controllers.gametypemenu;

import jhaturanga.controllers.Controller;
import jhaturanga.model.game.gametypes.GameTypesEnum;

public interface GameTypeMenuController extends Controller {

    int getNumberOfRow();

    int getNumberOfColumn();

    int getnNumbersOfGameTipes();
    
    void setGameType(GameTypesEnum gameType);
}
