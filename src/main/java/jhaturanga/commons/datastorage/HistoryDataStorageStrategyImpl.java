package jhaturanga.commons.datastorage;

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
    public Set<BoardState> getAllBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<BoardState> getBoard(final String id) {

        return null;
    }

}

//ObjectSerializer.saveToFile(arrayList, DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr");
//ObjectSerializer.loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr")
