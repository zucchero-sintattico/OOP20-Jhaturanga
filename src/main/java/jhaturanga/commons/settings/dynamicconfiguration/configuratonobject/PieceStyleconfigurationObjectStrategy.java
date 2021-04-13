package jhaturanga.commons.settings.dynamicconfiguration.configuratonobject;

import java.nio.file.Path;

public final class PieceStyleconfigurationObjectStrategy implements ConfigurationObjectStrategy {

    private final String folderName;

    public PieceStyleconfigurationObjectStrategy(final String directoryName) {
        this.folderName = directoryName;
    }

    @Override
    public Path getPath() {
        return Path.of("piece/");
    }

    @Override
    public String getName() {
        return folderName;
    }

    @Override
    public String getFileName() {
        return folderName;
    }

    @Override
    public Path getFilePath() {
        return Path.of(folderName);
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
