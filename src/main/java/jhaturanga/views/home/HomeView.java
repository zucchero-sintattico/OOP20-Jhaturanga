package jhaturanga.views.home;

import jhaturanga.controllers.home.HomeController;
import jhaturanga.views.JavaFXView;

/**
 * The Home Page View.
 */
public interface HomeView extends JavaFXView {

    default HomeController getHomeController() {
        return (HomeController) this.getController();
    }
}
