package jhaturanga.views.home;

import jhaturanga.controllers.home.HomeController;
import jhaturanga.views.View;

/**
 * The Home Page View.
 */
public interface HomeView extends View {

    default HomeController getHomeController() {
        return (HomeController) this.getController();
    }
}
