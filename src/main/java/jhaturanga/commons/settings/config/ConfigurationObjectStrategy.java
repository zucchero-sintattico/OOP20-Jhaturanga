package jhaturanga.commons.settings.config;

import java.nio.file.Path;

public interface ConfigurationObjectStrategy {

    /**
     * 
     * Get the path where are configuration file.
     * 
     * @return the root path of configuration folder
     * 
     *         example: my configuration file -> /root/user/.jhaturanga/css/dark.css
     *         this function return -> /root/user/.jhaturanga/css/
     * 
     */
    Path getPath();

    /**
     * 
     * @return the file path
     * 
     *         example: my configuration file -> /root/user/.jhaturanga/css/dark.css
     *         this function return -> /root/user/.jhaturanga/css/dark.css
     */
    Path getFilePath();

    /**
     * Gets the name.
     *
     * @return name of file without extension
     * 
     *         example: my configuration file -> /root/user/.jhaturanga/css/dark.css
     *         this function return -> dark
     */
    String getName();

    /**
     * Gets the file name.
     * 
     * @return name of configuration file
     * 
     *         example: my configuration file -> /root/user/.jhaturanga/css/dark.css
     *         this function return -> dark.css
     */
    String getFileName();

}
