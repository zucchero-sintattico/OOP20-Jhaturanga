package jhaturanga.controllers.online.match;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import jhaturanga.controllers.BasicController;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.model.Model;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.match.MatchEndType;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.match.online.OnlineMatch;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.timer.Timer;

/**
 * Basic implementation for the OnlineMatchController.
 *
 */
public final class OnlineMatchControllerImpl extends BasicController implements OnlineMatchController {

    private final MatchController matchController = new MatchControllerImpl();

    @Override
    public void setApplicationInstance(final Model applicationInstance) {
        super.setApplicationInstance(applicationInstance);
        this.matchController.setApplicationInstance(applicationInstance);
    }

    private void setHistoryIndexToLastBoard() {
        Optional<Board> history = this.getNextBoard();
        while (history.isPresent()) {
            history = this.getNextBoard();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnMovementHandler(final BiConsumer<PieceMovement, MovementResult> onMovementHandler) {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        netMatch.setOnMovementHandler((mov, res) -> {
            this.setHistoryIndexToLastBoard();
            onMovementHandler.accept(mov, res);
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWhitePlayer() {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        return netMatch.isWhitePlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMatch() {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        netMatch.exit();
        this.matchController.deleteMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnResignHandler(final Runnable onResign) {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        netMatch.setOnResign(onResign);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getLocalPlayer() {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        return netMatch.getLocalPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementResult move(final BoardPosition origin, final BoardPosition destination) {
        return this.matchController.move(origin, destination);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getWhitePlayer() {
        return this.matchController.getWhitePlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getBlackPlayer() {
        return this.matchController.getBlackPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> getWinner() {
        return this.matchController.getWinner();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerPair getPlayers() {
        return this.matchController.getPlayers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timer getTimer() {
        return this.matchController.getTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return this.matchController.getBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayerTurn() {
        return this.matchController.getPlayerTurn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.matchController.getPiecePossibleMoves(piece);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInNavigationMode() {
        return this.matchController.isInNavigationMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWhiteRemainingTime() {
        return this.matchController.getWhiteRemainingTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBlackRemainingTime() {
        return this.matchController.getBlackRemainingTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.matchController.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopTimer() {
        this.matchController.stopTimer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchStatus getStatus() {
        return this.matchController.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MatchEndType> getEndType() {
        return this.matchController.getEndType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMatch() throws IOException {
        this.matchController.saveMatch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMatchPresent() {
        return this.matchController.isMatchPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resign(final Player player) {
        this.matchController.resign(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getPreviousBoard() {
        return this.matchController.getPreviousBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getNextBoard() {
        return this.matchController.getNextBoard();
    }

}
