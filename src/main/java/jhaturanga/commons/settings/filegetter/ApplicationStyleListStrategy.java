package jhaturanga.commons.settings.filegetter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.settings.config.ApplicationStyleConfigStrategy;

public final class ApplicationStyleListStrategy extends PathFromDirectory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFolderPath() {
        return Path.of(DirectoryConfigurations.APPLICATION_STYLE_PATH);
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
    public List<ApplicationStyleConfigStrategy> getAll() {
        final List<ApplicationStyleConfigStrategy> applicationStyleList = new ArrayList<>();
        this.getDirectotyContent(this.getFolderPath().toString(), "css").stream().filter(elem -> elem.endsWith(".css"))
                .forEach(elem -> applicationStyleList.add(new ApplicationStyleConfigStrategy(elem)));

        return applicationStyleList;

    }

}
