package jhaturanga.model.user.datastorage;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.user.User;

/**
 * A Utility interface for saving and retrieving data from the storage.
 *
 */
public interface UsersDataStorageStrategy {

    /**
     * 
     * @param username to search
     * @return true if the user is present in the storage
     * @throws IOException 
     */
    boolean isPresent(String username) throws IOException;

    /**
     * 
     * @param username to get
     * @return the User if present, otherwise will return Optional.empty
     * @throws IOException 
     */
    Optional<User> getUserByUsername(String username) throws IOException;

    /**
     * 
     * @return all the users
     * @throws IOException 
     */
    Set<User> getAllUsers() throws IOException;

    /**
     * 
     * @param user to add or update in the storage
     * @throws IOException 
     */
    void put(User user) throws IOException;

    /**
     * 
     * @param username to remove
     * @return the removed User. If username is not present, the Optional will be empty
     * @throws IOException
     */
    Optional<User> remove(String username) throws IOException;
}
