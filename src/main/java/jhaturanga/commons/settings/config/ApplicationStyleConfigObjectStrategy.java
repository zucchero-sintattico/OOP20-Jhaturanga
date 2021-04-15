package jhaturanga.commons.settings.config;

import java.nio.file.Path;

import jhaturanga.commons.configurations.DirectoryConfigurations;

public final class ApplicationStyleConfigObjectStrategy implements ConfigurationObjectStrategy {

    private final String fileName;

    public ApplicationStyleConfigObjectStrategy(final String filename) {
        this.fileName = filename;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + "css/themes/");
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public String getName() {
        return getFileName().substring(0, getFileName().length() - 4);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public Path getFilePath() {
        return Path.of(this.getPath() + "/" + this.getFileName());
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
