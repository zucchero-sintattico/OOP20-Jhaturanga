package jhaturanga.commons.settings.config;

import java.nio.file.Path;

import jhaturanga.commons.DirectoryConfigurations;

public final class PieceStyleConfigStrategy implements ConfigurationObjectStrategy {

    private final String folderName;

    public PieceStyleConfigStrategy(final String directoryName) {
        this.folderName = directoryName;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.PIECE_STYLE_PATH);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getName() {
        return folderName;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getFileName() {
        return folderName;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Path getFilePath() {
        return Path.of(getPath() + "/" + folderName);
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
