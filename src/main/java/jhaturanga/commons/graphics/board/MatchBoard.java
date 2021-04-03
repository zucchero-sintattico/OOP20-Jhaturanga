package jhaturanga.commons.graphics.board;

import java.util.Set;
import java.util.function.Predicate;

import javafx.application.Platform;
import jhaturanga.commons.graphics.components.Tile;
import jhaturanga.commons.graphics.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.movement.NormalMatchPieceMovementStrategy;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.Piece;

public class MatchBoard extends GraphicalBoard {

    private final Runnable onMatchFinish;
    private final MatchController matchController;

    public MatchBoard(final MatchController matchController, final Runnable onMatchFinish) {
        super(matchController.getBoardStatus().getRows(), matchController.getBoardStatus().getColumns());

        this.matchController = matchController;
        this.onMatchFinish = onMatchFinish;
    }

    public final void setup() {
        this.setGraphicPieceMovementStrategy(new NormalMatchPieceMovementStrategy(this, this.matchController));
        this.setHistoryKeyHandlerStrategy(new NormalHistoryKeyHandlerStrategy(this, this.matchController));
        this.createBoard();
        this.redraw(this.matchController.getBoardStatus());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    /**
     * 
     * @param newBoard
     * @param movement
     * @param movementResult
     */
    public final void onMovement(final Board newBoard, final PieceMovement movement,
            final MovementResult movementResult) {
        this.resetHightlightedPositions();
        this.redraw(newBoard);
        this.highlightMovement(movement);
        Sound.play(SoundsEnum.fromMovementResult(movementResult));
        this.checkMatchStatus();
    }

    public final void highlightMovement(final PieceMovement movement) {
        this.resetHighlightedMovements();
        final Predicate<Tile> isPositionInvoledInMovement = (
                tile) -> tile.getBoardPosition().equals(movement.getOrigin())
                        || tile.getBoardPosition().equals(movement.getDestination());
        this.getTiles().stream().filter(isPositionInvoledInMovement).forEach(Tile::highlightMovement);
    }

    public final void resetHighlightedMovements() {
        this.getTiles().stream().forEach(Tile::resetHighlightMovement);
    }

    public final void hightlightPositons(final Set<BoardPosition> positions) {
        this.getTiles().stream().filter(x -> positions.contains(x.getBoardPosition())).forEach(x -> x.highlightPosition(
                this.matchController.getBoardStatus().getPieceAtPosition(x.getBoardPosition()).isPresent()));
    }

    public final void resetHightlightedPositions() {
        this.getTiles().forEach(Tile::resetHighlightPosition);
    }

    /**
     * 
     * @param piece
     */
    public final void drawPossibleDestinations(final Piece piece) {
        this.resetHightlightedPositions();
        this.hightlightPositons(this.matchController.getPiecePossibleMoves(piece));

    }

    private void checkMatchStatus() {
        if (this.matchController.getMatchStatus().equals(MatchStatus.ENDED)) {
            this.onMatchFinish.run();
        }
    }

}
