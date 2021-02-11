package jhaturanga.commons.datastorage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;

/**
 * This class provide utility to read users from JSON in the local storage.
 *
 */
public final class JsonUsersReaderImpl implements UsersReader {

    private Map<String, User> users;

    public JsonUsersReaderImpl() throws IOException {
        DirectoryConfigurations.validateUsersDataFile();
        final String jsonString = Files.readString(
                Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH), 
                StandardCharsets.UTF_8);
        final Type type = new TypeToken<Map<String, UserImpl>>() { }.getType();
        this.users = new Gson().fromJson(jsonString, type);
        if (this.users == null) {
            this.users = new HashMap<>();
        }
    }

    @Override
    public Map<String, User> getUsers() {
        return new HashMap<>(this.users);
    }

    @Override
    public Optional<User> getUserByUsername(final String username) {
        return Optional.ofNullable(this.users.get(username));
    }

}
