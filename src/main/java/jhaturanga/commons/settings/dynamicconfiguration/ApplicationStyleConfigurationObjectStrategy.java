package jhaturanga.commons.settings.dynamicconfiguration;

import java.nio.file.Path;

import jhaturanga.commons.configurations.DirectoryConfigurations;

public final class ApplicationStyleConfigurationObjectStrategy implements ConfigurationObjectStrategy {

    private final String fileName;

    public ApplicationStyleConfigurationObjectStrategy(final String filename) {
        this.fileName = filename;
    }

    @Override
    public Path getPath() {
        return Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + "css/themes/");
    }

    @Override
    public String getName() {
        return getFileName().substring(0, getFileName().length() - 4);
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Path getFilePath() {
        return Path.of(this.getPath() + "/" + this.getFileName());
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
