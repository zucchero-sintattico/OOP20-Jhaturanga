package jhaturanga.commons.settings.filegetter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.PieceStyleconfigurationObjectStrategy;

public final class PieceStyleListStrategy extends ConfigurationUtility {

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFolderPath() {
        return Path.of("pieces/");
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
    public List<PieceStyleconfigurationObjectStrategy> getAll() {
        final List<PieceStyleconfigurationObjectStrategy> applicationStyleList = new ArrayList<>();
        this.getDirectotyContent(this.getFolderPath().toString()).stream()
                .filter(elem -> Files.isDirectory(Path.of(elem)))
                .forEach(elem -> applicationStyleList.add(new PieceStyleconfigurationObjectStrategy(elem)));

        return applicationStyleList;

    }

}
