package jhaturanga.model.user.management;

import java.util.Collection;
import java.util.Optional;

import jhaturanga.model.user.User;

public interface UsersManager {

    /**
     * 
     * @param username to login the game
     * @param password to login the game
     * @return the user identified by user name and password.
     *         If there is no user find, the Optional will be empty
     */
    Optional<User> login(String username, String password);

    /**
     * 
     * @param username for future login
     * @param password for future login
     * @return the registered user. 
     *         If the user name already exist, the Optional will be empty
     */
    Optional<User> register(String username, String password);

    /**
     * 
     * @return a {@link java.util.Collections} of all Users
     */
    Collection<User> getAllUsers();
}
