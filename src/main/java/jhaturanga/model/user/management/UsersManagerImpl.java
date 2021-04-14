package jhaturanga.model.user.management;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.google.common.hash.Hashing;
import jhaturanga.model.user.datastorage.UsersDataStorageStrategy;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserBuilderImpl;

/**
 * This UsersManager accept a {@link UsersDataStorageStrategy} where will
 * retrieve and save users information.
 * 
 */
public final class UsersManagerImpl implements UsersManager {

    private final UsersDataStorageStrategy dataStorage;

    public UsersManagerImpl(final UsersDataStorageStrategy dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public Optional<User> login(final String username, final String password) throws IOException {
        final Optional<User> user = this.dataStorage.getUserByUsername(username);

        return user.filter(u -> u.getHashedPassword().isPresent())
                .filter(u -> u.getHashedPassword().get().equals(this.hashPassword(password)));
    }

    private String hashPassword(final String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    @Override
    public Optional<User> register(final String username, final String password) throws IOException {

        if (this.dataStorage.isPresent(username)) {
            return Optional.empty();
        }

        this.dataStorage
                .put(new UserBuilderImpl().username(username).hashedPassword(this.hashPassword(password)).build());

        return this.dataStorage.getUserByUsername(username);
    }

    @Override
    public Optional<User> delete(final String username) throws IOException {
        return this.dataStorage.remove(username);
    }


    @Override
    public Optional<User> put(final User user) throws IOException {
        if (!this.getForbidUsers().contains(user)) {
            this.dataStorage.put(user);
        }

        return this.dataStorage.getUserByUsername(user.getUsername());
    }

    @Override
    public Set<User> getAllUsers() throws IOException {
        return Collections.unmodifiableSet(this.dataStorage.getAllUsers());
    }

}
