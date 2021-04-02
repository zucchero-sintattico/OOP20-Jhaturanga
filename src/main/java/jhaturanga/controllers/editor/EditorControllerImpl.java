package jhaturanga.controllers.editor;

import java.util.Optional;

import jhaturanga.commons.PlayerPair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.instance.ApplicationInstance;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.editor.Editor;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.timer.DefaultsTimersEnum;

public final class EditorControllerImpl extends AbstractController implements EditorController {

    private final Editor editor = new EditorImpl();
    private final SetupController setupController = new SetupControllerImpl();

    @Override
    public void setApplicationInstance(final ApplicationInstance applicationInstance) {
        this.setupController.setApplicationInstance(applicationInstance);
        super.setApplicationInstance(applicationInstance);
    }

    @Override
    public void setTimer(final DefaultsTimersEnum timer) {
        this.setupController.setTimer(timer);
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.setupController.setGameType(gameType);
    }

    @Override
    public void setWhitePlayerChoice(final WhitePlayerChoice choice) {
        this.setupController.setWhitePlayerChoice(choice);
    }

    @Override
    public Optional<GameTypesEnum> getSelectedGameType() {
        return this.setupController.getSelectedGameType();
    }

    @Override
    public Optional<DefaultsTimersEnum> getSelectedTimer() {
        return this.setupController.getSelectedTimer();
    }

    @Override
    public Optional<WhitePlayerChoice> getSelectedWhitePlayerChoice() {
        return this.setupController.getSelectedWhitePlayerChoice();
    }

    @Override
    public void addPieceToBoard(final Piece piece) {
        this.editor.addPieceToBoard(piece);
    }

    @Override
    public Board getBoardStatus() {
        return this.editor.getBoardStatus();
    }

    @Override
    public void resetBoard(final int columns, final int rows) {
        this.editor.changeBoardDimensions(columns, rows);
    }

    @Override
    public boolean updatePiecePosition(final Piece piece, final BoardPosition position) {
        return this.editor.changePiecePosition(piece, position);
    }

    @Override
    public void removePieceAtPosition(final BoardPosition position) {
        this.editor.removePiece(position);
    }

    @Override
    public void createCustomizedStartingBoard() {
        this.editor.createStartingBoard();
    }

    @Override
    public boolean createMatch() {

        if (this.getSelectedTimer().isEmpty() || this.getSelectedGameType().isEmpty()
                || this.editor.getCreatedBoard().isEmpty()) {
            return false;
        }

        final PlayerPair players = this.getSelectedWhitePlayerChoice().get().getPlayers(
                this.getApplicationInstance().getFirstUser().get(),
                this.getApplicationInstance().getSecondUser().get());

        final Match match = new MatchBuilderImpl()
                .gameType(GameTypesEnum.CUSTOM_BOARD_VARIANT.getDynamicGameType(players,
                        this.editor.getCreatedBoard().get()))
                .timer(this.getSelectedTimer().get().getTimer(players)).build();

        this.getApplicationInstance().setMatch(match);

        return true;
    }

}
