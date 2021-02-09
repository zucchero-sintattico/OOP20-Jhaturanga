package jhaturanga.commons.datastorage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.model.user.User;

/**
 * This class provide utility to write users on JSON in the local storage.
 *
 */
public final class JsonUsersWriterImpl implements JsonUsersWriter {

    @Override
    public void putUser(final User user) throws IOException {
        final JsonUsersReader jsonUsersReader = new JsonUsersReaderImpl();
        final Map<String, User> users = jsonUsersReader.getUsers();
        users.put(user.getUserName(), user);
        this.replaceAllUser(users);
    }

    @Override
    public void replaceAllUser(final Map<String, User> users) throws IOException {
        final Type type = new TypeToken<Map<String, User>>() {

            /**
             * Need to avoid warning.
             */
            private static final long serialVersionUID = 1L; }.getType();
        final String json = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(users, type);

        final Path jsonPath = Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH);
        Files.deleteIfExists(jsonPath);
        Files.writeString(jsonPath, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }


}
