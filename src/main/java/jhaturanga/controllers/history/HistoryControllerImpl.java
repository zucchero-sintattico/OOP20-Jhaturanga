package jhaturanga.controllers.history;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.replay.Replay;
import jhaturanga.model.replay.SavedHistory;
import jhaturanga.model.replay.SavedHistoryImpl;

public final class HistoryControllerImpl extends AbstractController implements HistoryController {

    private final SavedHistory savedMatch = new SavedHistoryImpl();

    @Override
    public List<Replay> getAllSavedMatchDataOrder() {
        return this.savedMatch.getAllBoards().stream().sorted(Comparator.comparing(Replay::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void setReplay(final Replay boards) {

        this.getApplicationInstance().setReplay(boards);

    }

}
