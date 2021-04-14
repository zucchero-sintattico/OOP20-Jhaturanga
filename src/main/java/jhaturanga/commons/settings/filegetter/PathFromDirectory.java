package jhaturanga.commons.settings.filegetter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import jhaturanga.commons.configurations.DirectoryConfigurations;

public abstract class PathFromDirectory implements ConfigurationListStrategy {

    /**
     * 
     * @param folderPath
     * @return folder content.
     * @throws URISyntaxException
     */
    public List<String> getDirectotyContent(final String folderPath) {
        try {
            DirectoryConfigurations.validateResourcesDirectory();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.fileValidator("css");
        this.fileValidator("piece");

        final File folder = new File(folderPath);
        return folder == null ? List.of() : Arrays.asList(folder.list());

    }

    private void fileValidator(final String folderPath) {
        final Reflections reflections = new Reflections(folderPath, new ResourcesScanner());
        final Set<String> resourceList = reflections.getResources(x -> true);
        resourceList.forEach(elem -> {

            try {
                if (!Files.exists(Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + elem))) {
                    Files.copy(Path.of(ClassLoader.getSystemResource(elem).getPath()),
                            Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + elem));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
    }

    /**
     * 
     * @param folderPath
     * @return folder content.
     * @throws URISyntaxException
     */
//    public List<String> getDirectotyContent(final String folderPath) {
//        try {
//            DirectoryConfigurations.validateREsourcesDirectory();
//            copy(ClassLoader.getSystemResourceAsStream(folderPath),
//                    new FileOutputStream(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        final File folder = new File(folderPath);
//        return folder == null ? List.of() : Arrays.asList(folder.list());
//
//    }
}
