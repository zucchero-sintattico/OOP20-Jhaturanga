package jhaturanga.test.userstorage;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.datastorage.UsersReader;
import jhaturanga.commons.datastorage.JsonUsersReaderImpl;
import jhaturanga.commons.datastorage.UsersWriter;
import jhaturanga.commons.datastorage.JsonUsersWriterImpl;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;

class UserStorage {

    @BeforeAll
    static void init() throws IOException {
        final UsersWriter jw = new JsonUsersWriterImpl();
        jw.replaceAllUser(Map.of("1", new UserImpl("user1", "password1", 0, 0, 0), "2",
                new UserImpl("user2", "password2", 0, 0, 0), "3", new UserImpl("user3", "password3", 0, 0, 0)));
    }

    @Test
    void readerTest() throws IOException {
        final UsersReader jr = new JsonUsersReaderImpl();
        final Map<String, User> users = jr.getUsers();

        assertEquals(new UserImpl("user1", "password1", 0, 0, 0), users.get("1"));
        assertEquals(new UserImpl("user2", "password2", 0, 0, 0), users.get("2"));
        assertEquals(new UserImpl("user3", "password3", 0, 0, 0), users.get("3"));
        assertNull(users.get("4"));

        assertEquals(Optional.of(new UserImpl("user1", "password1", 0, 0, 0)), jr.getUserByUsername("1"));
        assertEquals(Optional.of(new UserImpl("user2", "password2", 0, 0, 0)), jr.getUserByUsername("2"));
        assertEquals(Optional.of(new UserImpl("user3", "password3", 0, 0, 0)), jr.getUserByUsername("3"));
    }

    @Test
    void writerTest() throws IOException {
        final UsersWriter jw = new JsonUsersWriterImpl();
        jw.putUser(new UserImpl("", "", 0, 0, 0));
        final UsersReader jr = new JsonUsersReaderImpl();

        assertEquals(new UserImpl("", "", 0, 0, 0), jr.getUserByUsername("").get());
        assertTrue(Files.isRegularFile(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH)));
    }

    @AfterAll
    static void end() throws IOException {
        Files.deleteIfExists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
    }
}
