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
     * @throws URISyntaxException
     */
    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.PIECE_STYLE_PATH);
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
