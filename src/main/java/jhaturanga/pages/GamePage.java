package jhaturanga.pages;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.game.GameController;

public final class GamePage implements Page {

    @Override
    public String getName() {
        return "Game";
    }

    @Override
    public Class<? extends Controller<? extends Page>> getControllerClass() {
        return GameController.class;
    }

}
