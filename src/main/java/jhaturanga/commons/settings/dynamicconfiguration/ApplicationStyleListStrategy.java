package jhaturanga.commons.settings.dynamicconfiguration;

import java.net.URISyntaxException;
import java.nio.file.Path;

import java.util.List;

import java.util.stream.Collectors;

public final class ApplicationStyleListStrategy extends ConfigurationUtility {

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFolderPath() {
        return Path.of("css/themes/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Path> getAllPath() {
        return this.getAllName().stream().map(e -> getFolderPath().toString().concat(e)).map(e -> Path.of(e))
                .collect(Collectors.toList());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllName() {
        try {
            return this.getDirectotyContent(getFolderPath().toString()).stream().filter(elem -> elem.contains(".css"))
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return List.of();
    }

}
