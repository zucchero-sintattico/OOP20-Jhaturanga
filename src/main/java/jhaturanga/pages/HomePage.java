package jhaturanga.pages;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.home.HomeController;

public final class HomePage implements Page {

    @Override
    public String getName() {
        return "Home";
    }

    @Override
    public Class<? extends Controller<? extends Page>> getControllerClass() {
        return HomeController.class;
    }

}
