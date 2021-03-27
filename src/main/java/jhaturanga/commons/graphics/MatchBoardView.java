package jhaturanga.commons.graphics;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.editor.PieceRectangleImpl;

public final class MatchBoardView extends Pane {

    private static final double PIECE_SCALE = 2;

    private final GraphicPieceMovementStrategy pieceMovementStrategy;
    private final PieceImageLoader imageLoader = new PieceImageLoader();
    private final GridPane grid = new GridPane();
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Set<TileImpl> tilesOnBoard = new HashSet<>();

    private final AbstractJavaFXView matchView;

    private final Runnable onMatchFinish;
    private final boolean isWhiteBottom;
    private final DoubleBinding tileDimension;

    public MatchBoardView(final AbstractJavaFXView matchView, final Runnable onMatchFinish) {
        this(matchView, onMatchFinish, true, false);
    }

    public MatchBoardView(final AbstractJavaFXView matchView, final Runnable onMatchFinish,
            final boolean isWhiteBottom) {
        this(matchView, onMatchFinish, isWhiteBottom, false);
    }

    public MatchBoardView(final AbstractJavaFXView matchView, final Runnable onMatchFinish, final boolean isWhiteBottom,
            final boolean isOnline) {

        this.matchView = matchView;
        this.onMatchFinish = onMatchFinish;
        this.isWhiteBottom = isWhiteBottom;
        this.pieceMovementStrategy = new GraphicPieceMovementStrategyImpl(this, this.grid, isWhiteBottom, isOnline);
        this.tileDimension = this.widthProperty().divide(Math.max(this.getMatchController().getBoardStatus().getRows(),
                this.getMatchController().getBoardStatus().getRows()));

        this.setupHistoryKeysHandler();
        this.getChildren().add(this.grid);
        this.drawBoard(this.getMatchController().getBoardStatus());
        this.redraw(this.getMatchController().getBoardStatus());

        Platform.runLater(() -> this.grid.requestFocus());
    }

    private void setupHistoryKeysHandler() {
        this.grid.setOnKeyPressed(new HistoryKeyHandlerStrategyImpl(this, this.getMatchController()));
    }

    private void playSound(final MovementResult movementResult) {
        Sound.play(SoundsEnum.fromMovementResult(movementResult));
    }

    public void onMovement(final Board newBoard, final Movement movement, final MovementResult movementResult) {
        this.redraw(newBoard);
        this.highlightMovement(movement);
        this.playSound(movementResult);
        this.checkMatchStatus();
    }

    private void drawBoard(final Board board) {
        IntStream.range(0, board.getRows()).forEach(row -> {
            IntStream.range(0, board.getColumns()).forEach(col -> {
                final TileImpl tile = new TileImpl(
                        this.getGridCoordinateFromBoardPosition(new BoardPositionImpl(col, row)), this.tileDimension,
                        this.getMatchController().getBoardStatus().getRows());
                this.tilesOnBoard.add(tile);
                this.grid.add(tile, col, row);
            });
        });
    }

    private void highlightMovement(final Movement movement) {
        final Predicate<TileImpl> isPartOfMovement = (tile) -> tile.getBoardPosition().equals(movement.getOrigin())
                || tile.getBoardPosition().equals(movement.getDestination());
        this.tilesOnBoard.stream().forEach(TileImpl::resetMovementHighlight);
        this.tilesOnBoard.stream().filter(isPartOfMovement).forEach(TileImpl::highlightMovement);
    }

    private void checkMatchStatus() {
        if (!this.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE)) {
            this.onMatchFinish.run();
        }
    }

    public void resetHightlightedPositions() {
        this.tilesOnBoard.forEach(TileImpl::resetHighlightPosition);
    }

    public void drawPossibleDestinations(final PieceRectangleImpl piece) {
        // I need to save the possible moves here to avoid recalculating them each time
        // the predicate is tested in the Function.
        final Set<BoardPosition> pieceMoves = this.getMatchController().getPiecePossibleMoves(piece.getPiece());
        final Predicate<BoardPosition> isPiecePresent = (pos) -> this.getMatchController().getBoardStatus()
                .getPieceAtPosition(pos).isPresent();
        this.resetHightlightedPositions();
        this.tilesOnBoard.stream().filter(x -> pieceMoves.contains(x.getBoardPosition()))
                .forEach(x -> x.highlightPosition(isPiecePresent.test(x.getBoardPosition())));
    }

    private BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final int row = this.isWhiteBottom ? this.getMatchController().getBoardStatus().getRows() - 1 - position.getY()
                : position.getY();
        return new BoardPositionImpl(position.getX(), row);
    }

    private void addPieceToBoard(final PieceRectangleImpl piece) {
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.grid.add(piece, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(piece, HPos.CENTER);
    }

    private void drawPiece(final Piece piece) {
        final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(piece, this.imageLoader.getPieceImage(piece),
                this.tileDimension.divide(PIECE_SCALE), this.pieceMovementStrategy);
        this.pieces.add(pieceViewPort);
        this.addPieceToBoard(pieceViewPort);
    }

    public boolean isPieceMovable() {
        return !this.getMatchController().isInNavigationMode()
                && this.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE);
    }

    public void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces);
        board.getPiecesStatus().forEach(this::drawPiece);
    }

    public MatchController getMatchController() {
        return (MatchController) this.matchView.getController();
    }
}
