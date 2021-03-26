package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.user.User;

public interface HomeController extends Controller {

    /**
     * Returns the first User if logged.
     * 
     * @return fist User logged
     */
    Optional<User> getFirstUser();

    /**
     * Returns the second User if logged.
     * 
     * @return second User logged
     */
    Optional<User> getSecondUser();

}
