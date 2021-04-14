package jhaturanga.test.model.dynamicconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.settings.filegetter.PathFromDirectory;

public class ConfigurationUtilityTest {

    private final PathFromDirectory testConfigurationUtility = new PathFromDirectory() {

        @Override
        public List<Path> getAllPath() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<String> getAll() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Path getFolderPath() {
            return Path.of("css/themes/");
        }

    };

    @Test
    void styleTest() {
        try {
            assertTrue(testConfigurationUtility.getDirectotyContent("css/themes/").contains("light.css"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
