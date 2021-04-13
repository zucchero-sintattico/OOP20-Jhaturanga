package jhaturanga.commons.settings.dynamicconfiguration;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public abstract class ConfigurationUtility implements ConfigurationListStrategy {

    /**
     * 
     * @param folderPath
     * @return folder content.
     * @throws URISyntaxException
     */
    public List<String> getDirectotyContent(final String folderPath) throws URISyntaxException {
        final File folder = new File(ClassLoader.getSystemResource(folderPath).toURI());
        return folder == null ? List.of() : Arrays.asList(folder.list());
    }
}
