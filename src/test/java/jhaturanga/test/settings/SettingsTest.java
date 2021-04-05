package jhaturanga.test.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.commons.settings.storage.ApplicationStyleDateStorageJasonStrategy;
import jhaturanga.commons.settings.storage.PiecesStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SettingsDataStorageStrategy;

public class SettingsTest {

    private final SettingsDataStorageStrategy<ApplicationStyleEnum> styleSet = new ApplicationStyleDateStorageJasonStrategy();
    private final SettingsDataStorageStrategy<PieceStyleEnum> pieceSet = new PiecesStyleDateStorageJsonStrategy();

    @Test
    void styleSetTest() throws IOException {

        this.styleSet.setSetting(ApplicationStyleEnum.DARK);
        final SettingsDataStorageStrategy<ApplicationStyleEnum> styleSetNew = new ApplicationStyleDateStorageJasonStrategy();
        assertEquals(styleSetNew.getSetting().get(), ApplicationStyleEnum.DARK);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void pieceSetTest() throws IOException {
        this.pieceSet.setSetting(PieceStyleEnum.CLASSIC);
        assertEquals(pieceSet.getSetting().get(), PieceStyleEnum.CLASSIC);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void dateSetTest() throws IOException {
        this.pieceSet.setSetting(PieceStyleEnum.CLASSIC);
        this.styleSet.setSetting(ApplicationStyleEnum.DARK);
        this.pieceSet.setSetting(PieceStyleEnum.SHADOW);
        final SettingsDataStorageStrategy<ApplicationStyleEnum> styleSetNew = new ApplicationStyleDateStorageJasonStrategy();
        final SettingsDataStorageStrategy<PieceStyleEnum> pieceSetNew = new PiecesStyleDateStorageJsonStrategy();
        assertEquals(pieceSetNew.getSetting().get(), PieceStyleEnum.SHADOW);
        assertEquals(styleSetNew.getSetting().get(), ApplicationStyleEnum.DARK);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));
    }

}
