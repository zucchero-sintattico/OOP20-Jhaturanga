package jhaturanga.commons.settings.filegetter;

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
    public List<String> getDirectotyContent(final String folderPath) {
        try {
            final File folder = new File(ClassLoader.getSystemResource(folderPath).toURI());
            return folder == null ? List.of() : Arrays.asList(folder.list());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return List.of();
    }
}
