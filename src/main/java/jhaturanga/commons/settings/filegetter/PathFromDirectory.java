package jhaturanga.commons.settings.filegetter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import jhaturanga.commons.DirectoryConfigurations;

public abstract class PathFromDirectory implements ConfigurationListStrategy {
    private static final int BUFFER = 1024;

    /**
     * 
     * @param folderPath          - home directory where you read your resource
     * @param resourcesRootFolder - resource folder which wont copy in home
     *                            directory
     * @return list of home directory resource path.
     * 
     *         Example: folderPath = file:///home/.jhaturanga/res/css/thems
     *         resourcesRootFolder = jar://css
     * 
     *         file of resourcesRootFolder will copy on folderPath, but the function
     *         return also folderPath file list.
     * 
     *         Set default home directory in
     *         DirectoryConfigurations.validateResourcesDirectory()
     * 
     */
    public List<String> getDirectotyContent(final String folderPath, final String resourcesRootFolder) {
        try {
            DirectoryConfigurations.validateResourcesDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileValidator(resourcesRootFolder);

        final File folder = new File(folderPath);
        final String[] files = folder.list();
        return files == null ? List.of() : Arrays.asList(files);

    }

    private void fileValidator(final String folderPath) {
        final Reflections reflections = new Reflections(folderPath, new ResourcesScanner());
        final Set<String> resourceFileList = reflections.getResources(x -> true);
        resourceFileList.forEach(resourceFile -> {

            try {
                if (!Files.exists(
                        Path.of(Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + resourceFile).toUri()))) {

                    copy(ClassLoader.getSystemResourceAsStream(resourceFile),
                            new FileOutputStream(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + resourceFile));

                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        });
    }

    private static void copy(final InputStream instream, final FileOutputStream outstream) {

        try {

            final byte[] buffer = new byte[BUFFER];

            int length;
            /*
             * copying the contents from input stream to output stream using read and write
             * methods
             */
            for (length = instream.read(buffer); length > 0; length = instream.read(buffer)) {
                outstream.write(buffer, 0, length);
            }

            // Closing the input/output file streams
            instream.close();
            outstream.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
