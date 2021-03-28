package jhaturanga.test.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.datastorage.settings.ApplicationStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.PiecesStyleDateStorageJasonStrategy;
import jhaturanga.commons.datastorage.settings.SettingsDataStorageStrategy;

public class SettingsTest {

    private static final String BLACK = "black";
    private final SettingsDataStorageStrategy styleSet = new ApplicationStyleDateStorageJasonStrategy();
    private final SettingsDataStorageStrategy pieceSet = new PiecesStyleDateStorageJasonStrategy();

    @Test
    void styleSetTest() throws IOException {

        this.styleSet.setSetting(BLACK);

        final SettingsDataStorageStrategy styleSetNew = new ApplicationStyleDateStorageJasonStrategy();

        assertEquals(styleSetNew.getSetting(), BLACK);

    }

    @Test
    void pieceSetTest() throws IOException {

        this.pieceSet.setSetting("shadow");

        assertEquals(pieceSet.getSetting(), "shadow");

    }

    @Test
    void dateSetTest() throws IOException {

        this.pieceSet.setSetting("shadow");
        this.styleSet.setSetting("black");
        this.pieceSet.setSetting("classic");

        final SettingsDataStorageStrategy styleSetNew = new ApplicationStyleDateStorageJasonStrategy();
        final SettingsDataStorageStrategy pieceSetNew = new PiecesStyleDateStorageJasonStrategy();

        assertEquals(pieceSetNew.getSetting(), "classic");
        assertEquals(styleSetNew.getSetting(), "black");

    }

}
