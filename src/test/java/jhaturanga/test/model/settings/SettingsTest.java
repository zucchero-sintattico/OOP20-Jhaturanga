package jhaturanga.test.model.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.settings.media.style.application.ApplicationStyleEnum;
import jhaturanga.commons.settings.media.style.piece.PieceStyleEnum;
import jhaturanga.commons.settings.storage.ApplicationStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.PiecesStyleDateStorageJsonStrategy;
import jhaturanga.commons.settings.storage.SettingsDataStorageJsonStrategy;

public class SettingsTest {

    private final SettingsDataStorageJsonStrategy<ApplicationStyleEnum> styleSet = new ApplicationStyleDateStorageJsonStrategy();
    private final SettingsDataStorageJsonStrategy<PieceStyleEnum> pieceSet = new PiecesStyleDateStorageJsonStrategy();

    @Test
    void styleSetTest() throws IOException {

        this.styleSet.setSetting(ApplicationStyleEnum.DARK);
        final SettingsDataStorageJsonStrategy<ApplicationStyleEnum> styleSetNew = new ApplicationStyleDateStorageJsonStrategy();
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
        final SettingsDataStorageJsonStrategy<ApplicationStyleEnum> styleSetNew = new ApplicationStyleDateStorageJsonStrategy();
        final SettingsDataStorageJsonStrategy<PieceStyleEnum> pieceSetNew = new PiecesStyleDateStorageJsonStrategy();
        assertEquals(pieceSetNew.getSetting().get(), PieceStyleEnum.SHADOW);
        assertEquals(styleSetNew.getSetting().get(), ApplicationStyleEnum.DARK);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));
    }

}
