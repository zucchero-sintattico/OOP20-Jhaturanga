package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.user.User;

/**
 * The controller for the home page view.
 */
public interface HomeController extends Controller {

    /**
     * Returns the first User if logged.
     * 
     * @return fist logged user if present, an Optional.empty otherwise
     */
    Optional<User> getFirstUser();

    /**
     * Returns the second User if logged.
     * 
     * @return this second logged user if present, an Optional.empty otherwise
     */
    Optional<User> getSecondUser();

}
