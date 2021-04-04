package jhaturanga.test.userstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.commons.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerImpl;

class UsersManagerTest {

    private UsersManager manager;

    @BeforeAll
    static void setUp() throws IOException  {
        if (Files.exists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH))) {
            Files.copy(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH), 
                    Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH + ".tmp"));
        }
    }

    @BeforeEach
    void init() throws IOException {
        Files.deleteIfExists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
        manager = new UsersManagerImpl(new UsersDataStorageJsonStrategy());
    }

    @Test
    void loginNoFile() throws IOException {
        assertEquals(Optional.empty(), manager.login("user", "pass"));
        assertEquals(0, this.getNumberOfRegistered());
    }

    @Test
    void normalLogin() throws IOException {
        manager.register("user1", "pass1");

        assertEquals("user1", manager.login("user1", "pass1").get().getUsername());
        assertEquals(1, this.getNumberOfRegistered());
    }

    @Test
    void badNameOrPasswordLogin() throws IOException {
        manager.register("user2", "pass2");

        assertEquals(Optional.empty(), manager.login("user2", "pass1"));
        assertEquals(Optional.empty(), manager.login("user3", "pass3"));
        assertEquals(1, this.getNumberOfRegistered());
    }

    @Test
    void registerSameUsername() throws IOException {
        manager.register("user4", "pass4");

        assertEquals(Optional.empty(), manager.register("user4", "pass4"));
        assertEquals(Optional.empty(), manager.register("user4", "pass5"));
        assertEquals(1, this.getNumberOfRegistered());
    }

    @Test
    void registerMoreUsers() throws IOException {
        assertEquals("user6", manager.register("user6", "pass6").get().getUsername());
        assertEquals("user7", manager.register("user7", "pass7").get().getUsername());
        assertEquals("user8", manager.register("user8", "pass8").get().getUsername());
        assertEquals(3, this.getNumberOfRegistered());
    }

    @Test
    void guestTest() {
        assertEquals(new UserImpl("GUEST", null, 0, 0, 0), UsersManager.GUEST);
    }

    @Test
    void computerTest() {
        assertEquals(new UserImpl("COMPUTER", null, 0, 0, 0), UsersManager.COMPUTER);
    }

    @Test
    void deleteUser() throws IOException {
        final String name = "user9";
        manager.register(name, "pass9");

        assertEquals("user9", manager.delete(name).get().getUsername());
        assertEquals(Optional.empty(), manager.delete(name));
    }

    @Test
    void deleteNoUser() throws IOException {
        assertEquals(Optional.empty(), manager.delete("user10"));
    }

    private int getNumberOfRegistered() throws IOException {
        return this.manager.getAllUsers().size();
    }

    @Test
    void putUser() throws IOException {
        final User user = new UserImpl("", null, 0, 0, 0);
        assertEquals(Optional.of(user), manager.put(user));
    }

    @Test
    void forbidUsersTest() throws IOException {
        assertTrue(manager.getForbidUsers().contains(UsersManager.GUEST));
        assertTrue(manager.getForbidUsers().contains(UsersManager.COMPUTER));
    }

    @Test
    void forbidPut() throws IOException {
        assertEquals(Optional.empty(), manager.put(UsersManager.GUEST));
        assertEquals(Optional.empty(), manager.put(UsersManager.COMPUTER));
    }

    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
    }

    @AfterAll
    static void restore() throws IOException {
        if (Files.exists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH + ".tmp"))) {
            Files.move(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH + ".tmp"),
                    Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
        }
    }
}
