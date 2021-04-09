package jhaturanga.controllers.history;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.replay.ReplayData;
import jhaturanga.model.replay.SavedReplay;
import jhaturanga.model.replay.SavedReplayImpl;

public final class HistoryControllerImpl extends BasicController implements HistoryController {

    private final SavedReplay savedMatch = new SavedReplayImpl();

    @Override
    public List<ReplayData> getAllSavedReplaysOrdered() {
        return this.savedMatch.getAllBoards().stream().sorted(Comparator.comparing(ReplayData::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void setReplay(final ReplayData boards) {

        this.getApplicationInstance().setReplay(boards);

    }

}
