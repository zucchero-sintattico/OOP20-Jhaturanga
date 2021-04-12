package jhaturanga.controllers.editor;

import java.util.Optional;

import jhaturanga.controllers.BasicController;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.Model;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.editor.Editor;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.timer.DefaultTimers;

/**
 * Basic implementation of the EditorController.
 *
 */
public final class EditorControllerImpl extends BasicController implements EditorController {

    private final Editor editor = new EditorImpl();
    private final SetupController setupController = new SetupControllerImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationInstance(final Model applicationInstance) {
        this.setupController.setApplicationInstance(applicationInstance);
        super.setApplicationInstance(applicationInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimer(final DefaultTimers timer) {
        this.setupController.setTimer(timer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameType(final GameType gameType) {
        this.setupController.setGameType(gameType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWhitePlayerChoice(final WhitePlayerChoice choice) {
        this.setupController.setWhitePlayerChoice(choice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameType> getSelectedGameType() {
        return this.setupController.getSelectedGameType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DefaultTimers> getSelectedTimer() {
        return this.setupController.getSelectedTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<WhitePlayerChoice> getSelectedWhitePlayerChoice() {
        return this.setupController.getSelectedWhitePlayerChoice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPieceToBoard(final Piece piece) {
        this.editor.addPieceToBoard(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoardStatus() {
        return this.editor.getBoardStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetBoard(final int columns, final int rows) {
        this.editor.changeBoardDimensions(columns, rows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updatePiecePosition(final Piece piece, final BoardPosition position) {
        return this.editor.changePiecePosition(piece, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePieceAtPosition(final BoardPosition position) {
        this.editor.removePiece(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCustomizedStartingBoard() {
        this.editor.createStartingBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createMatch() {

        if (this.getSelectedTimer().isEmpty() || this.getSelectedGameType().isEmpty()
                || this.editor.getCreatedBoard().isEmpty()
                || this.editor.getCreatedBoard().get().getBoard().isBlank()) {
            return false;
        }

        final PlayerPair players = this.getSelectedWhitePlayerChoice().get().getPlayers(
                this.getApplicationInstance().getFirstUser().get(),
                this.getApplicationInstance().getSecondUser().get());

        final Match match = new MatchImpl(
                new GameFactoryImpl().customizedBoardVariantGame(players, this.editor.getCreatedBoard().get()),
                this.getSelectedTimer().get().getTimer(players));

        this.getApplicationInstance().setMatch(match);

        return true;
    }

}
