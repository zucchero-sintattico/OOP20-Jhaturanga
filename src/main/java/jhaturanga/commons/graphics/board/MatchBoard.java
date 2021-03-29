package jhaturanga.commons.graphics.board;

import java.util.Set;
import java.util.function.Predicate;

import javafx.application.Platform;
import jhaturanga.commons.graphics.components.TileImpl;
import jhaturanga.commons.graphics.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.movement.NormalMatchPieceMovementStrategy;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.views.editor.PieceRectangleImpl;
import jhaturanga.views.match.MatchView;
import jhaturanga.views.match.MatchViewImpl;

public class MatchBoard extends GraphicalBoard {

    private final MatchView matchView;
    private final Runnable onMatchFinish;

    public MatchBoard(final MatchView matchView, final Runnable onMatchFinish) {

        super(matchView.getMatchController().getBoardStatus().getRows(),
                matchView.getMatchController().getBoardStatus().getColumns());

        this.matchView = matchView;
        this.onMatchFinish = onMatchFinish;
    }

    /**
     * 
     */
    public void setup() {
        this.setPieceMovementStrategy(new NormalMatchPieceMovementStrategy(this));
        this.getGrid().setOnKeyPressed(new NormalHistoryKeyHandlerStrategy(this, matchView.getMatchController()));

        this.drawBoard();
        this.redraw(this.getMatchController().getBoardStatus());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    /**
     * 
     * @param newBoard
     * @param movement
     * @param movementResult
     */
    public void onMovement(final Board newBoard, final Movement movement, final MovementResult movementResult) {
        this.resetHightlightedPositions();
        this.redraw(newBoard);
        this.highlightMovement(movement);
        this.playSound(movementResult);
        this.checkMatchStatus();
    }

    private void highlightMovement(final Movement movement) {
        final Predicate<TileImpl> isPartOfMovement = (tile) -> tile.getBoardPosition().equals(movement.getOrigin())
                || tile.getBoardPosition().equals(movement.getDestination());
        this.getTiles().stream().forEach(TileImpl::resetHighlightMovement);
        this.getTiles().stream().filter(isPartOfMovement).forEach(TileImpl::highlightMovement);
    }

    private void checkMatchStatus() {
        if (!this.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE)) {
            this.onMatchFinish.run();
        }
    }

    /**
     * 
     */
    public void resetHightlightedPositions() {
        this.getTiles().forEach(TileImpl::resetHighlightPosition);
    }

    /**
     * 
     * @param piece
     */
    public void drawPossibleDestinations(final PieceRectangleImpl piece) {
        // I need to save the possible moves here to avoid recalculating them each time
        // the predicate is tested in the Function.
        final Set<BoardPosition> pieceMoves = this.getMatchController().getPiecePossibleMoves(piece.getPiece());
        final Predicate<BoardPosition> isPiecePresent = (pos) -> this.getMatchController().getBoardStatus()
                .getPieceAtPosition(pos).isPresent();
        this.resetHightlightedPositions();
        this.getTiles().stream().filter(x -> pieceMoves.contains(x.getBoardPosition()))
                .forEach(x -> x.highlightPosition(isPiecePresent.test(x.getBoardPosition())));
    }

    /**
     * 
     * @return the match controller
     */
    public MatchController getMatchController() {
        return (MatchController) this.matchView.getController();
    }
}
