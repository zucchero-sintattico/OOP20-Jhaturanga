package jhaturanga.commons.settings.config;

import java.nio.file.Path;

import jhaturanga.commons.configurations.DirectoryConfigurations;
public final class PieceStyleConfigObjectStrategy implements ConfigurationObjectStrategy {

    private final String folderName;

    public PieceStyleConfigObjectStrategy(final String directoryName) {
        this.folderName = directoryName;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + "piece/");
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public String getName() {
        return folderName;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public String getFileName() {
        return folderName;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public Path getFilePath() {
        return Path.of(getPath() + "/" + folderName);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public String toString() {
        return this.getName();
    }

}
