package jhaturanga.test.model.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.commons.settings.SettingMediator;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;
import jhaturanga.commons.settings.filegetter.PieceStyleListStrategy;


public class SettingsTest {
    private final PieceStyleListStrategy myPieceStyleList = new PieceStyleListStrategy();
    private final ApplicationStyleListStrategy myApplicationStyleList = new ApplicationStyleListStrategy();

    @Test
    void styleSetTest() throws IOException {
        SettingMediator.setAndSaveApplicationStyle(myApplicationStyleList.getAll().get(0));
        assertEquals(SettingMediator.getSavedApplicatioStyle().getName(), myApplicationStyleList.getAll().get(0).getName());
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void pieceSetTest() throws IOException {
        SettingMediator.setAndSavePieceStyle(myPieceStyleList.getAll().get(0));
        assertEquals(SettingMediator.getSavedPieceStyle().getName(), myPieceStyleList.getAll().get(0).getName());
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));

    }

    @Test
    void dateSetTest() throws IOException {
        SettingMediator.setAndSavePieceStyle(myPieceStyleList.getAll().get(0));
        SettingMediator.setAndSaveApplicationStyle(myApplicationStyleList.getAll().get(0));
        SettingMediator.setAndSavePieceStyle(myPieceStyleList.getAll().get(1));
        assertEquals(SettingMediator.getSavedPieceStyle().getName(), myPieceStyleList.getAll().get(1).getName());
        assertEquals(SettingMediator.getSavedApplicatioStyle().getName(), myApplicationStyleList.getAll().get(0).getName());
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));
    }

}
