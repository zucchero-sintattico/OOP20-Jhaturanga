package jhaturanga.commons.datastorage;

import java.io.IOException;
import java.util.Map;
import jhaturanga.model.user.User;

/**
 * A utility interface for writing on Json data storage.
 *
 */
public interface JsonUsersWriter {

    /**
     * 
     * @param user to add or update in the local storage
     * @throws IOException 
     */
    void putUser(User user) throws IOException;

    /**
     * 
     * @param users to set the local storage
     */
    void replaceAllUser(Map<String, User> users) throws IOException;

}
