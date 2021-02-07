package jhaturanga.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DirectoryConfigurations {

    private static final String CONFIGURATION_DIRECTORY_NAME = ".jhaturanga";
    private static final String USER_DIRECTORY = System.getProperty("user.home");

    private static final String SEPARATOR = File.separator;

    /**
     * Represent the path of the installation directory.
     */
    public static final String CONFIGURATION_DIRECTORY_PATH = 
            USER_DIRECTORY 
            + SEPARATOR 
            + CONFIGURATION_DIRECTORY_NAME;

    private DirectoryConfigurations() {
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
}
