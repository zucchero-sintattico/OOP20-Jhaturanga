package jhaturanga.commons.datastorage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;

/**
 * This class provide to save and retrieve data from storage using Json.
 *
 */
public final class UsersDataStorageJsonStrategy implements UsersDataStorageStrategy {

    private Map<String, User> users;

    public UsersDataStorageJsonStrategy() throws IOException {
        DirectoryConfigurations.validateUsersDataFile();
        final String jsonString = Files.readString(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH),
                StandardCharsets.UTF_8);
        final Type type = new TypeToken<Map<String, UserImpl>>() {
        }.getType();
        this.users = new Gson().fromJson(jsonString, type);
        if (this.users == null) {
            this.users = new HashMap<>();
        }
    }

    private void save() throws IOException {
        final Type type = new TypeToken<Map<String, User>>() {
        }.getType();
        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(users, type);

        final Path jsonPath = Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH);
        Files.deleteIfExists(jsonPath);
        Files.writeString(jsonPath, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    @Override
    public boolean isPresent(final String username) {
        return this.users.containsKey(username);
    }

    @Override
    public Optional<User> getUserByUsername(final String username) {
        return Optional.ofNullable(this.users.get(username));
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>(this.users.values());
    }

    @Override
    public void put(final User user) throws IOException {
        this.users.put(user.getUsername(), user);
        this.save();
    }

    @Override
    public Optional<User> remove(final String username) throws IOException {
        final Optional<User> removed = Optional.ofNullable(this.users.remove(username));
        if (removed.isPresent()) {
            this.save();
        }
        return removed;
    }

}
