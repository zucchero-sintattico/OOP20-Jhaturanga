package jhaturanga.pages;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.splash.SplashControllerImpl;

public final class SplashPage implements Page {

    @Override
    public String getName() {
        return "Splash";
    }

    @Override
    public <T extends Page> Controller<T> getNewController() {
        return (Controller<T>) new SplashControllerImpl();
    }

}
