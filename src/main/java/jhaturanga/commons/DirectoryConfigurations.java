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
    private static final String USERS_DATA_NAME = "users.json";
    private static final String HISTORY_DIRECTORY_NAME = "history";
    private static final String RESOURCES_DIRECTORY_NAME = "res";
    private static final String SETTINGS_DIRECTORY_NAME = "settings";
    private static final String SETTINGS_DATA_NAME = "settings.json";
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
            + HISTORY_DIRECTORY_NAME + SEPARATOR;

    /**
     * Represent the path of the history directory.
     */
    public static final String RESOURCES_DIRECTORY_PATH = CONFIGURATION_DIRECTORY_PATH + SEPARATOR
            + RESOURCES_DIRECTORY_NAME + SEPARATOR;

    /**
     * Represent the path of the application style directory.
     */
    public static final String APPLICATION_STYLE_PATH = RESOURCES_DIRECTORY_PATH + "css/themes/";

    /**
     * Represent the path of the piece style directory.
     */
    public static final String PIECE_STYLE_PATH = RESOURCES_DIRECTORY_PATH + "piece/";

    /**
     * Represent the path of the settings directory.
     */
    public static final String SETTINGS_DIRECTORY_PATH = CONFIGURATION_DIRECTORY_PATH + SEPARATOR
            + SETTINGS_DIRECTORY_NAME;

    /**
     * Represent the path of the settings data file.
     */
    public static final String SETTINGS_DATA_FILE_PATH = SETTINGS_DIRECTORY_PATH + SEPARATOR + SETTINGS_DATA_NAME;

    private DirectoryConfigurations() {
    }

    private static void fileValidator(final String path) throws IOException {
        DirectoryConfigurations.validateUsersDataDirectory();

        if (!Files.isRegularFile(Path.of(path))) {
            Files.deleteIfExists(Path.of(path));
        }

        if (Files.notExists(Path.of(path))) {
            Files.createFile(Path.of(path));
        }
    }

    private static void directoryValidator(final String path) throws IOException {
        DirectoryConfigurations.validateInstallationDirectory();

        if (!Files.isDirectory(Path.of(path))) {
            Files.deleteIfExists(Path.of(path));
        }

        if (Files.notExists(Path.of(path))) {
            Files.createDirectory(Path.of(path));
        }
    }

    /**
     * This utility method will check if the configuration directory exist,
     * otherwise will create it.
     * 
     * @throws IOException
     */
    public static void validateInstallationDirectory() throws IOException {

        if (!Files.isDirectory(Path.of(CONFIGURATION_DIRECTORY_PATH))) {
            Files.deleteIfExists(Path.of(CONFIGURATION_DIRECTORY_PATH));
        }

        if (Files.notExists(Path.of(CONFIGURATION_DIRECTORY_PATH))) {
            Files.createDirectory(Path.of(CONFIGURATION_DIRECTORY_PATH));
        }

    }

    /**
     * This utility method will check if the data users directory exist, otherwise
     * will create it.
     * 
     * @throws IOException
     */
    public static void validateUsersDataDirectory() throws IOException {

        directoryValidator(USERS_DATA_DIRECTORY_PATH);
    }

    /**
     * This utility method will check if the data users file exist, otherwise will
     * create it.
     * 
     * @throws IOException
     */
    public static void validateUsersDataFile() throws IOException {

        fileValidator(USERS_DATA_FILE_PATH);
    }

    /**
     * This utility method will check if the history directory exist, otherwise will
     * create it.
     * 
     * @throws IOException
     */
    public static void validateHistoryDirectory() throws IOException {

        directoryValidator(HISTORY_DIRECTORY_PATH);

    }

    /**
     * This utility method will check if the resources directory exist, otherwise
     * will create it.
     * 
     * @throws IOException
     */
    public static void validateResourcesDirectory() throws IOException {

        directoryValidator(RESOURCES_DIRECTORY_PATH);
        directoryValidator(RESOURCES_DIRECTORY_PATH + "css/");
        directoryValidator(RESOURCES_DIRECTORY_PATH + "css/pages/");
        directoryValidator(APPLICATION_STYLE_PATH);
        directoryValidator(PIECE_STYLE_PATH);
        directoryValidator(RESOURCES_DIRECTORY_PATH + "piece/classic/");
        directoryValidator(RESOURCES_DIRECTORY_PATH + "piece/shadow/");
    }

    /**
     * This utility method will check if the settings directory exist, otherwise
     * will create it.
     * 
     * @throws IOException
     */
    public static void validateSettingsDirectory() throws IOException {

        directoryValidator(SETTINGS_DIRECTORY_PATH);
    }

    /**
     * This utility method will check if the data users file exist, otherwise will
     * create it.
     * 
     * @throws IOException
     */
    public static void validateSettingsDataFile() throws IOException {

        fileValidator(SETTINGS_DATA_FILE_PATH);
    }
}
