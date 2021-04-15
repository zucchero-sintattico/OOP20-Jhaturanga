package jhaturanga.commons.settings.config;

import java.nio.file.Path;

import jhaturanga.commons.DirectoryConfigurations;


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
        return Path.of(DirectoryConfigurations.APPLICATION_STYLE_PATH);
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
