package jhaturanga.test.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.datastorage.settings.ApplicationStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.PiecesStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.SettingsDataStorageStrategy;

public class SettingsTest {

    private static final String BLACK = "BLACk";
    private final SettingsDataStorageStrategy styleSet = new ApplicationStyleDateStorageJasonStrategy();
    private final SettingsDataStorageStrategy pieceSet = new PiecesStyleDateStorageJasonStrategy();

    @Test
    void styleSetTest() throws IOException {

        this.styleSet.setSetting(BLACK);
        final SettingsDataStorageStrategy styleSetNew = new ApplicationStyleDateStorageJasonStrategy();
        assertEquals(styleSetNew.getSetting().get(), BLACK);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void pieceSetTest() throws IOException {
        this.pieceSet.setSetting("SHADOW");
        assertEquals(pieceSet.getSetting().get(), "SHADOW");
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void dateSetTest() throws IOException {
        this.pieceSet.setSetting("SHADOW");
        this.styleSet.setSetting(BLACK);
        this.pieceSet.setSetting("CLASSIC");
        final SettingsDataStorageStrategy styleSetNew = new ApplicationStyleDateStorageJasonStrategy();
        final SettingsDataStorageStrategy pieceSetNew = new PiecesStyleDateStorageJasonStrategy();
        assertEquals(pieceSetNew.getSetting().get(), "CLASSIC");
        assertEquals(styleSetNew.getSetting().get(), BLACK);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));
    }

}
