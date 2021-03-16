package jhaturanga.commons.datastorage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.ObjectSerializer;
import jhaturanga.model.savedhistory.BoardState;

public final class HistoryDataStorageStrategy {

    private HistoryDataStorageStrategy() {

    }

    public static void put(final BoardState match, final String id) throws IOException {
        DirectoryConfigurations.validateInstallationDirectory();
        ObjectSerializer.saveToFile(match, DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr");
    }

    public static Optional<Set<BoardState>> getAllBoard() {
        final File folder = new File(DirectoryConfigurations.HISTORY_DIRECTORY_PATH);
        final Set<BoardState> myBoards = new HashSet<>();
        for (final File fileEntry : folder.listFiles()) {
            if (getBoardByPath(fileEntry.getName()).isPresent()) {
                myBoards.add(getBoardByPath(fileEntry.getName()).get());
            }
        }
        return Optional.of(myBoards);
    }

    public static Optional<BoardState> getBoard(final String id) {
        if (ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhat").isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((BoardState) ObjectSerializer
                .loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhat").get());
    }

    private static Optional<BoardState> getBoardByPath(final String id) {
        if (ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                (BoardState) ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id).get());
    }
}
