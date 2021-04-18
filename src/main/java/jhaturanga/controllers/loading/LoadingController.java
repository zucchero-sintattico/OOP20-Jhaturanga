package jhaturanga.controllers.loading;

import jhaturanga.controllers.Controller;

/**
 * The controller for the loading page.
 */
public interface LoadingController extends Controller {

    /**
     * Load the application's data. This method should be call on application
     * startup.
     */
    void load();
}
