package jhaturanga.test.model.dynamicconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;

public class DynamicConfigTest {

    private final ApplicationStyleListStrategy testConfigurationUtility = new ApplicationStyleListStrategy();

    @Test
    void styleDirectoyTest() {

        assertTrue(testConfigurationUtility.getDirectoryContent(DirectoryConfigurations.APPLICATION_STYLE_PATH, "css")
                .contains("dark.css"));

    }
}
