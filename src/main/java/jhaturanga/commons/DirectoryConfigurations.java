package jhaturanga.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is an utility class for the installation directory.
 *
 */
public final class DirectoryConfigurations {

    private static final String CONFIGURATION_DIRECTORY_NAME = ".jhaturanga";
    private static final String USERS_DATA_DIRECTORY_NAME = "users_data";
    private static final String HISTORY_DIRECTORY_NAME = "history";
    private static final String USERS_DATA_NAME = "users.json";
    private static final String USER_DIRECTORY = System.getProperty("user.home");
    private static final String SEPARATOR = File.separator;

    /**
     * Represent the path of the installation directory.
     */
    public static final String CONFIGURATION_DIRECTORY_PATH = USER_DIRECTORY + SEPARATOR + CONFIGURATION_DIRECTORY_NAME;

    /**
     * Represent the path of the users data directory.
     */
    public static final String USERS_DATA_DIRECTORY_PATH = CONFIGURATION_DIRECTORY_PATH + SEPARATOR
            + USERS_DATA_DIRECTORY_NAME;

    /**
     * Represent the path of the users data file.
     */
    public static final String USERS_DATA_FILE_PATH = USERS_DATA_DIRECTORY_PATH + SEPARATOR + USERS_DATA_NAME;

    /**
     * Represent the path of the history directory.
     */
    public static final String HISTORY_DIRECTORY_PATH = CONFIGURATION_DIRECTORY_PATH + SEPARATOR
            + HISTORY_DIRECTORY_NAME;

    private DirectoryConfigurations() {
    }

    /**
     * This utility method will check if the configuration directory exist,
     * otherwise will create it.
     * 
     * @throws IOException
     */
    public static void validateInstallationDirectory() throws IOException {
        System.out.println("creo dir");
        if (!Files.isDirectory(Path.of(CONFIGURATION_DIRECTORY_PATH))) {
            Files.deleteIfExists(Path.of(CONFIGURATION_DIRECTORY_PATH));
        }

        if (Files.notExists(Path.of(CONFIGURATION_DIRECTORY_PATH))) {
            Files.createDirectory(Path.of(CONFIGURATION_DIRECTORY_PATH));
        }

        if (!Files.isDirectory(Path.of(HISTORY_DIRECTORY_PATH))) {
            Files.deleteIfExists(Path.of(HISTORY_DIRECTORY_PATH));
        }
    }

    /**
     * This utility method will check if the data users directory exist, otherwise
     * will create it.
     * 
     * @throws IOException
     */
    public static void validateUsersDataDirectory() throws IOException {
        DirectoryConfigurations.validateInstallationDirectory();

        if (!Files.isDirectory(Path.of(USERS_DATA_DIRECTORY_PATH))) {
            Files.deleteIfExists(Path.of(USERS_DATA_DIRECTORY_PATH));
        }

        if (Files.notExists(Path.of(USERS_DATA_DIRECTORY_PATH))) {
            Files.createDirectory(Path.of(USERS_DATA_DIRECTORY_PATH));
        }
    }

    /**
     * This utility method will check if the data users file exist, otherwise will
     * create it.
     * 
     * @throws IOException
     */
    public static void validateUsersDataFile() throws IOException {
        DirectoryConfigurations.validateUsersDataDirectory();

        if (!Files.isRegularFile(Path.of(USERS_DATA_FILE_PATH))) {
            Files.deleteIfExists(Path.of(USERS_DATA_FILE_PATH));
        }

        if (Files.notExists(Path.of(USERS_DATA_FILE_PATH))) {
            Files.createFile(Path.of(USERS_DATA_FILE_PATH));
        }
    }
}
