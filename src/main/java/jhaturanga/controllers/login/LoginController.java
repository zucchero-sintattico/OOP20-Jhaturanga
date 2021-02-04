package jhaturanga.controllers.login;

import jhaturanga.controllers.Controller;

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
     * @return true if login succeeded, false otherwise
     */
    Boolean login(String username, String password);

    /**
     * Attempt to register a user.
     * 
     * @param username - the username of the user
     * @param password - the password of the user
     * @return true if register process succeeded, false otherwise
     */
    Boolean register(String username, String password);
}
