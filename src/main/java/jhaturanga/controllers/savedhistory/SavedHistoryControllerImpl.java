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

public final class SavedHistoryControllerImpl extends AbstractController implements SavedHistoryController {

    private final SavedHistory savedMatch = new SavedHistoryImpl();

    @Override
    public List<BoardState> getAllSavedMatchDataOrder() {
        return this.savedMatch.getAllBoards().stream().sorted(Comparator.comparing(BoardState::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public void play(final BoardState boards) {
        this.getModel().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, boards.getBlackUser()));
        this.getModel().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, boards.getWhiteUser()));
        this.getModel().setGameType(boards.getGameType());
        if (boards.getGameType().equals(GameTypesEnum.CUSTOM_BOARD_VARIANT)) {
            this.getModel().setDynamicGameTypeStartingBoard(
                    new EditorImpl().startingBoardFromString(boards.getBoards().get(0)));
        }
        this.getModel().createMatch();
        this.getModel().getActualMatch().get().uploadMatchHistory(boards.getBoards());
    }

}
