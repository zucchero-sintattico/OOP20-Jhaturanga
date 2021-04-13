package jhaturanga.commons.settings.dynamicconfiguration;

import java.nio.file.Path;
import java.util.List;

public interface ConfigurationListStrategy {

    /**
     * 
     * @return a list of path of all file and directory.
     */
    List<Path> getAllPath();

    /**
     * 
     * @return a list of name of all file and directory.
     */
    List<String> getAllName();

    /**
     * 
     * @return selected folder path
     */
    Path getFolderPath();
}
