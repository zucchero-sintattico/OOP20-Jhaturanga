package jhaturanga.model.replay;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.commons.ObjectSerializer;

public final class ReplayDataStorage {

    private static final String HISTORY_FILE_EXTENSION = ".jhat";

    private ReplayDataStorage() {

    }

    public static void put(final ReplayData match, final String id) throws IOException {
        DirectoryConfigurations.validateInstallationDirectory();
        DirectoryConfigurations.validateHistoryDirectory();
        ObjectSerializer.saveToFile(match, DirectoryConfigurations.HISTORY_DIRECTORY_PATH + id + ".jhtr");
    }

    public static Set<ReplayData> getAllBoard() {
        final File folder = new File(DirectoryConfigurations.HISTORY_DIRECTORY_PATH);
        final File[] files = folder.listFiles();
        return files == null ? Set.of()
                : Arrays.stream(files).map(File::getName).map(ReplayDataStorage::getBoardByPath)
                        .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }

    public static Optional<ReplayData> getBoard(final String id) {
        return ReplayDataStorage.getBoardByPath(id + HISTORY_FILE_EXTENSION);
    }

    private static Optional<ReplayData> getBoardByPath(final String path) {
        final Optional<Object> element = ObjectSerializer
                .loadFromFile(DirectoryConfigurations.HISTORY_DIRECTORY_PATH + path);

        return element.isEmpty() ? Optional.empty() : Optional.of((ReplayData) element.get());
    }
}
