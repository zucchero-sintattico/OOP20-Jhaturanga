package jhaturanga.controllers.home;

import jhaturanga.controllers.Controller;
import jhaturanga.model.user.User;

public interface HomeController extends Controller {

    /**
     * Get the first logged user.
     * 
     * @return the first user.
     */
    User getFirstUser();
}
