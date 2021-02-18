package jhaturanga.controllers.login;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.user.User;

/**
 * The controller for the login page. Coordinate the login transaction between
 * View and Model.
 */
public interface LoginController extends Controller {

    /**
     * Attempt to login a user.
     * 
     * @param username - the username of the user
     * @param password - the password of the user
     * @return the user if login succeeded, Optional.empty otherwise
     */
    Optional<User> login(String username, String password);

    /**
     * Attempt to register a user.
     * 
     * @param username - the username of the user
     * @param password - the password of the user
     * @return the user if register process succeeded, Optional.empty otherwise
     */
    Optional<User> register(String username, String password);

    void logGuestUser();
}
