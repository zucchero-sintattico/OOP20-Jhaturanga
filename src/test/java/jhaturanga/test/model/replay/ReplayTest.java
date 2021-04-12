package jhaturanga.test.model.replay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.model.board.BoardImpl;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.replay.ReplayBuilder;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.model.replay.SavedReplay;
import jhaturanga.model.replay.SavedReplayImpl;

import jhaturanga.model.user.UserImpl;

public class ReplayTest {
    private final SavedReplay replaySaved = new SavedReplayImpl();

    private final ReplayData replay = new ReplayBuilder().blackUser(new UserImpl("nero", "nero", 0, 0, 0))
            .whiteUser(new UserImpl("bianco", "bianco", 0, 0, 0)).date(new Date()).gameType(GameType.CLASSIC)
            .matchID("test").boards(List.of(new BoardImpl(Set.of(), 8, 8))).build();

    @Test
    void replaySaved() throws IOException {

        final int initalFile = replaySaved.getAllBoards().size();
        this.replaySaved.save(replay);
        assertEquals(replaySaved.getAllBoards().size(), initalFile + 1);
        Files.deleteIfExists(
                Path.of(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + "/" + this.replay.getMatchID() + ".jhtr"));

        assertEquals(replaySaved.getAllBoards().size(), initalFile);

    }

}
