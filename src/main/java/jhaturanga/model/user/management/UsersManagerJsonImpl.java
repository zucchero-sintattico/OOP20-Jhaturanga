package jhaturanga.model.user.management;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.model.user.User;

/**
 * This class will retrieve and save data in the application directory through
 * JSON files.
 */
public final class UsersManagerJsonImpl implements UsersManager {

    //private final Map<Pair<String, String>, User> users;

    private UsersManagerJsonImpl() {
    }

    public static UsersManager getInstance() throws IOException {
        DirectoryConfigurations.validateUsersDataFile();
        return new UsersManagerJsonImpl();
    }


    @Override
    public Optional<User> login(final String username, final String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> register(final String username, final String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

}
