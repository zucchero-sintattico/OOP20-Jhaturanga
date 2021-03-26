package jhaturanga.model.user.management;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.user.User;
import jhaturanga.model.user.UserBuilderImpl;

/**
 * Base Interface to manage the user persistent data such as login and registration.
 *
 */
public interface UsersManager {

    /**
     * Default user for no login.
     */
    User GUEST = new UserBuilderImpl().username("GUEST").build();

    /**
     * Default user for COMPUTER.
     */
    User COMPUTER = new UserBuilderImpl().username("COMPUTER").build();

    /**
     * 
     * @param username to login the game
     * @param password to login the game
     * @return the user identified by user name and password. If there is no user
     *         find, the Optional will be empty
     * @throws IOException 
     */
    Optional<User> login(String username, String password) throws IOException;

    /**
     * 
     * @param username for future login
     * @param password for future login
     * @return the registered user. If the user name already exist, the Optional
     *         will be empty
     */
    Optional<User> register(String username, String password) throws IOException;

    /**
     * 
     * @param username to delete
     * @return the deleted user. If there is not user with the given name,
     *  will return an empty Optional
     * @throws IOException
     */
    Optional<User> delete(String username) throws IOException;

    /**
     * 
     * @return a {@link java.util.Set} of all Users
     */
    Set<User> getAllUsers() throws IOException;

}
