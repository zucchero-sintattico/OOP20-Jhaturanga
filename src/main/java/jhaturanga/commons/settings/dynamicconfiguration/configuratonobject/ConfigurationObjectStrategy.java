package jhaturanga.commons.settings.dynamicconfiguration.configuratonobject;

import java.nio.file.Path;

public interface ConfigurationObjectStrategy {

    /**
     * 
     * @return the root path of configuration folder
     * 
     */
    Path getPath();

    /**
     * 
     * @return the file path
     */
    Path getFilePath();

    /**
     * 
     * @return name of file without extension
     */
    String getName();

    /**
     * 
     * @return name of configuration file
     */
    String getFileName();

}
