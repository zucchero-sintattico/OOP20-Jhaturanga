package jhaturanga.commons.settings.config;

import java.nio.file.Path;

import jhaturanga.commons.DirectoryConfigurations;


public final class ApplicationStyleConfigStrategy implements ConfigurationObjectStrategy {

    private final String fileName;

    public ApplicationStyleConfigStrategy(final String filename) {
        this.fileName = filename;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.APPLICATION_STYLE_PATH);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getName() {
        return getFileName().substring(0, getFileName().length() - 4);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Path getFilePath() {
        return Path.of(this.getPath() + "/" + this.getFileName());
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String toString() {
        return this.getName();
    }

}
