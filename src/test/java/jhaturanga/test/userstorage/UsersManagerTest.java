package jhaturanga.test.userstorage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.datastorage.JsonUsersReaderImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerJsonImpl;

class UsersManagerTest {

    private UsersManager manager;

    @BeforeEach
    void init() throws IOException {
        manager = new UsersManagerJsonImpl();
        Files.deleteIfExists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
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

    private int getNumberOfRegistered() throws IOException {
        return new JsonUsersReaderImpl().getUsers().size();
    }

    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Path.of(DirectoryConfigurations.USERS_DATA_FILE_PATH));
    }
}