package jhaturanga.commons.settings.filegetter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;

public final class ApplicationStyleListStrategy extends PathFromDirectory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFolderPath() {
        return Path.of(DirectoryConfigurations.RESOURCES_DIRECTORY_PATH + "css/themes/");
    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public List<Path> getAllPath() {
        return this.getAll().stream().map(e -> e.getFilePath()).collect(Collectors.toList());

    }

    /**
     * {@inheritDoc}
     * 
     * @throws URISyntaxException
     */
    @Override
    public List<ApplicationStyleConfigurationObjectStrategy> getAll() {
        final List<ApplicationStyleConfigurationObjectStrategy> applicationStyleList = new ArrayList<>();
        this.getDirectotyContent(this.getFolderPath().toString()).stream().filter(elem -> elem.endsWith(".css"))
                .forEach(elem -> applicationStyleList.add(new ApplicationStyleConfigurationObjectStrategy(elem)));

        return applicationStyleList;

    }

}
