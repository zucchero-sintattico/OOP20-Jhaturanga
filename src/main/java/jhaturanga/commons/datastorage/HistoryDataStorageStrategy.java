package jhaturanga.commons.datastorage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.ObjectSerializer;
import jhaturanga.model.replay.Replay;

public final class HistoryDataStorageStrategy {

    private static final String HISTORY_FILE_EXTENSION = ".jhat";

    private HistoryDataStorageStrategy() {

    }

    public static void put(final Replay match, final String id) throws IOException {
        DirectoryConfigurations.validateInstallationDirectory();
        ObjectSerializer.saveToFile(match, DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr");
    }

    public static Optional<Set<Replay>> getAllBoard() {
        final File folder = new File(DirectoryConfigurations.HISTORY_DIRECTORY_PATH);
        final File[] files = folder.listFiles();

        return files == null ? Optional.empty()
                : Optional.of(Arrays.stream(files).map(File::getName).map(HistoryDataStorageStrategy::getBoardByPath)
                        .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet()));
    }

    public static Optional<Replay> getBoard(final String id) {
        return HistoryDataStorageStrategy.getBoardByPath(id + HISTORY_FILE_EXTENSION);
    }

    private static Optional<Replay> getBoardByPath(final String path) {
        final Optional<Object> element = ObjectSerializer
                .loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + path);

        return element.isEmpty() ? Optional.empty() : Optional.of((Replay) element.get());
    }
}
