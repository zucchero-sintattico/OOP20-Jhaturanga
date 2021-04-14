package jhaturanga.test.model.dynamicconfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;


public class DynamicConfigTest {

    private final ApplicationStyleListStrategy testConfigurationUtility = new ApplicationStyleListStrategy();

    @Test
    void styleTest() {

        assertTrue(testConfigurationUtility.getDirectotyContent("css/themes/").contains("light.css"));

    }
}
