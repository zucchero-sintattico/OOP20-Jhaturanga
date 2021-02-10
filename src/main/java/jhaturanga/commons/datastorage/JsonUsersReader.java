package jhaturanga.commons.datastorage;

import java.util.Map;
import java.util.Optional;

import jhaturanga.model.user.User;

/**
 * A utility interface for reading on Json data storage.
 * 
 */
public interface JsonUsersReader {

    /**
     * 
     * @return the map that matches the unique usernames with the users data
     */
    Map<String, User> getUsers();

    /**
     * 
     * @param username to search
     * @return the user data if present
     */
    Optional<User> getUserByUsername(String username);

}
