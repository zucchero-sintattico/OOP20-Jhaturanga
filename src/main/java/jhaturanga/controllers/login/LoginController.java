package jhaturanga.controllers.login;

import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
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
     * @return true if the user was register, false otherwise
     */
    boolean login(String username, String password);

    /**
     * Attempt to register a user.
     * 
     * @param username - the username of the user
     * @param password - the password of the user
     * @return true if the user was register, false otherwise
     */
    boolean register(String username, String password);

    /**
     * Validate the password and get the response.
     * 
     * @param password - the password to be validated
     * @return the validation result
     */
    ValidationResult validatePassword(String password);

    /**
     * Validate the username and get the response.
     * 
     * @param username - the username to be validated
     * @return the validation result
     */
    ValidationResult validateUsername(String username);

    /**
     * log user as GUEST.
     */
    void loginAsGuest();

}
