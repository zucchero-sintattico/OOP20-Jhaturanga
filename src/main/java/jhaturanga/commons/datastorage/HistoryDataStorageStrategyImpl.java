package jhaturanga.commons.datastorage;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.ObjectSerializer;
import jhaturanga.model.savedhistory.BoardState;

public final class HistoryDataStorageStrategyImpl implements HistoryDataStorageStrategy {

    @Override
    public void put(final BoardState match, final String id) {
        ObjectSerializer.saveToFile(match, DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr");
    }

    @Override
    public Optional<Set<BoardState>> getAllBoard() {
        final File folder = new File(DirectoryConfigurations.HISTORY_DIRECTORY_PATH);
        final Set<BoardState> myBoards = new HashSet<>();
        for (final File fileEntry : folder.listFiles()) {
            if (this.getBoardByPath(fileEntry.getName()).isPresent()) {
                myBoards.add(this.getBoardByPath(fileEntry.getName()).get());
            }
        }
        return Optional.of(myBoards);
    }

    @Override
    public Optional<BoardState> getBoard(final String id) {
        if (ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhat").isEmpty()) {
            return Optional.empty();
        }
        return Optional.of((BoardState) ObjectSerializer
                .loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhat").get());
    }

    private Optional<BoardState> getBoardByPath(final String id) {
        if (ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                (BoardState) ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id).get());
    }
}
