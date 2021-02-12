package jhaturanga.model.user.management;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.google.common.hash.Hashing;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.datastorage.UsersReader;
import jhaturanga.commons.datastorage.JsonUsersReaderImpl;
import jhaturanga.commons.datastorage.UsersWriter;
import jhaturanga.commons.datastorage.JsonUsersWriterImpl;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserBuilderImpl;
/**
 * This class will retrieve and save data in the application 
 * directory through JSON files.
 */
public final class UsersManagerJsonImpl implements UsersManagerFacade {


    public UsersManagerJsonImpl() throws IOException {
        DirectoryConfigurations.validateUsersDataFile();
    }

    @Override
    public Optional<User> login(final String username, final String password) throws IOException {
        final UsersReader jsonUsersReader = new JsonUsersReaderImpl();
        final Optional<User> user = jsonUsersReader.getUserByUsername(username);

        return user.filter(u -> u.getHashedPassword().isPresent())
                .filter(u -> u.getHashedPassword().get().equals(this.hashPassword(password)));
    }

    private String hashPassword(final String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public Optional<User> register(final String username, final String password)  throws IOException {
        Optional<User> registeredUser = Optional.empty();
        final Map<String, User> users = this.getUsersMap();

        if (!users.containsKey(username)) {
            registeredUser = Optional.of(new UserBuilderImpl()
                    .username(username)
                    .hashedPassword(this.hashPassword(password))
                    .build());
            final UsersWriter jsonUsersWriter = new JsonUsersWriterImpl();
            jsonUsersWriter.putUser(registeredUser.get());
        }

        return registeredUser;
    }

    private Map<String, User> getUsersMap() throws IOException {
        return new JsonUsersReaderImpl().getUsers();
    }

    @Override
    public Optional<User> delete(final String username) throws IOException {
        final Map<String, User> users = this.getUsersMap();

        final Optional<User> removed = Optional.ofNullable(users.remove(username));
        final UsersWriter jsonUsersWriter = new JsonUsersWriterImpl();
        jsonUsersWriter.replaceAllUser(users);

        return removed;
    }

    @Override
    public Collection<User> getAllUsers() throws IOException {
        return Collections.unmodifiableCollection(this.getUsersMap().values());
    }

}
