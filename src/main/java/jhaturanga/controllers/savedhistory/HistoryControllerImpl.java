package jhaturanga.controllers.savedhistory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.savedhistory.BoardState;
import jhaturanga.model.savedhistory.SavedHistory;
import jhaturanga.model.savedhistory.SavedHistoryImpl;

public final class HistoryControllerImpl extends AbstractController implements HistoryController {

    private final SavedHistory savedMatch = new SavedHistoryImpl();

    @Override
    public List<BoardState> getAllSavedMatchDataOrder() {
        return this.savedMatch.getAllBoards().stream().sorted(Comparator.comparing(BoardState::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void play(final BoardState boards) {
        this.getApplicationInstance().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, boards.getBlackUser()));
        this.getApplicationInstance().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, boards.getWhiteUser()));
        this.getApplicationInstance().setGameType(boards.getGameType());
        if (boards.getGameType().equals(GameTypesEnum.CUSTOM_BOARD_VARIANT)) {
            this.getApplicationInstance()
                    .setDynamicGameTypeStartingBoard(new EditorImpl().stringBoardFromNormal(boards.getBoards().get(0)));
        }
        this.getApplicationInstance().createMatch();
        this.getApplicationInstance().getActualMatch().get().uploadMatchHistory(boards.getBoards());
    }

}
