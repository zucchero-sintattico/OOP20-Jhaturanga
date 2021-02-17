package jhaturanga.views.splash;

import jhaturanga.controllers.splash.SplashController;
import jhaturanga.views.View;

public interface SplashView extends View {

    /**
     * Get the splash page controller.
     * 
     * @return the controller
     */
    SplashController getSplashController();
}
