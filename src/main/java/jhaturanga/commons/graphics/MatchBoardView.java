package jhaturanga.commons.graphics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.editor.PieceRectangleImpl;

public final class MatchBoardView extends Pane {

    private static final double PIECE_SCALE = 2;

    private final PieceImageLoader imageLoader = new PieceImageLoader();

    private final GridPane grid = new GridPane();
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Set<TileImpl> tilesHighlighted = new HashSet<>();
    private final Set<TileImpl> tilesOnBoard = new HashSet<>();

    private boolean isPieceBeingDragged;
    private final AbstractJavaFXView matchView;

    private final Runnable onMatchFinish;
    private final boolean isWhiteBottom;
    private final boolean isOnline;

    private final Function<Predicate<BoardPosition>, Set<TileImpl>> getTilesThatRespectPredicate = (
            predicate) -> this.tilesOnBoard.stream().filter(e -> predicate.test(e.getBoardPosition()))
                    .collect(Collectors.toSet());

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
        this.isOnline = isOnline;

        this.setupHistoryKeysHandler();
        this.getChildren().add(this.grid);
        this.drawBoard(this.getMatchController().getBoardStatus());
        this.redraw(this.getMatchController().getBoardStatus());

        Platform.runLater(() -> this.grid.requestFocus());
    }

    public void makeMovement(final Movement movement, final MovementResult result) {
        System.out.println("MAKE MOVEMENT");
        this.redraw(this.getMatchController().getBoardStatus());
        Sound.play(SoundsEnum.valueOf(result.toString()));

        // highligthMovement(movement.getOrigin(), movement.getDestination());
    }

    /*
     * Setup the handler for history navigation
     */
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
    }

    /**
     * Draw the board.
     * 
     * @param board - the board to be drew
     */
    private void drawBoard(final Board board) {
        final int bigger = Integer.max(board.getColumns(), board.getRows());
        final List<String> letters = Arrays.asList("a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" "));

        IntStream.range(0, board.getRows()).forEach(row -> {
            IntStream.range(0, board.getColumns()).forEach(col -> {

                final TileImpl tile = new TileImpl(
                        this.getGridCoordinateFromBoardPosition(new BoardPositionImpl(col, row)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));

                if (row == board.getRows() - 1) {
                    final Label label = new Label(letters.get(col));
                    tile.getChildren().add(label);
                    final double marginRight = 3.0;
                    final double marginBottom = 1.0;
                    label.layoutXProperty()
                            .bind(tile.widthProperty().subtract(label.widthProperty()).subtract(marginRight));
                    label.layoutYProperty()
                            .bind(tile.heightProperty().subtract(label.heightProperty()).subtract(marginBottom));
                    label.setTextFill((row + col) % 2 == 0 ? Color.BLACK : Color.WHITE);
                }
                if (col == 0) {
                    final Label label = new Label(String.valueOf(board.getRows() - row));
                    tile.getChildren().add(label);
                    final double marginTop = 2.0;
                    final double marginLeft = 2.0;
                    label.layoutXProperty().set(marginLeft);
                    label.layoutYProperty().set(marginTop);
                    label.setTextFill((row + col) % 2 == 0 ? Color.BLACK : Color.WHITE);
                }
                this.tilesOnBoard.add(tile);
                this.grid.add(tile, col, row);
            });
        });
    }

    private boolean isLocalPlayerPiece(final Piece piece) {
        return this.isWhiteBottom ? this.getMatchController().getWhitePlayer().equals(piece.getPlayer())
                : this.getMatchController().getBlackPlayer().equals(piece.getPlayer());
    }

    private void detachPieceFromGrid(final PieceRectangleImpl piece) {
        if (this.grid.getChildren().contains(piece)) {
            this.grid.getChildren().remove(piece);
            this.getChildren().add(piece);
        }
    }

    /**
     * Piece Click Handler. This should hint all the position where the piece can
     * go.
     */
    private void onPieceClick(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getStage().getScene().setCursor(Cursor.OPEN_HAND);
        this.detachPieceFromGrid(piece);

        if (this.getMatchController().getPlayerTurn().equals(piece.getPiece().getPlayer()) && this.isPieceMovable()) {
            this.drawPossibleDestinations(piece);
        }
    }

    /**
     * On piece dragged handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which is dragged
     */
    private void onPieceDragged(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
        if (this.isPieceMovable()) {
            this.isPieceBeingDragged = true;
            piece.setX(event.getX() - piece.getWidth() / 2);
            piece.setY(event.getY() - piece.getHeight() / 2);
        }
    }

    /**
     * On piece release handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which is dragged
     */
    private void onPieceReleased(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getStage().getScene().setCursor(Cursor.DEFAULT);

        final BoardPosition startingPos = piece.getPiece().getPiecePosition();
        final BoardPosition position = this.getBoardPositionsFromGridCoordinates(event.getSceneX(), event.getSceneY());
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;
            // Get the piece moved
            final Piece movedPiece = piece.getPiece();
            // Check if the engine accept the movement
            final MovementResult result = this.getMatchController().move(movedPiece.getPiecePosition(), position);

            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.getChildren().remove(piece);
                this.grid.add(piece, realPosition.getX(), realPosition.getY());
                this.onMovement(this.getMatchController().getBoardStatus(),
                        new MovementImpl(movedPiece, startingPos, position), result);
            } else {
                this.abortMove(piece);
            }

            this.grid.requestFocus();
            this.redraw(this.getMatchController().getBoardStatus());
            this.resetHightlightedPositions();
            this.checkMatchStatus();
        } else {
            this.abortMove(piece);
        }
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

    private void resetHightlightedPositions() {
        this.tilesOnBoard.forEach(TileImpl::resetHighlightPosition);
    }

    private void drawPossibleDestinations(final PieceRectangleImpl piece) {
        // I need to save the possible moves here to avoid recalculating them each time
        // the predicate is tested in the Function.
        final Set<BoardPosition> pieceMoves = this.getMatchController().getPiecePossibleMoves(piece.getPiece());
        final Predicate<BoardPosition> isPiecePresent = (pos) -> this.getMatchController().getBoardStatus()
                .getPieceAtPosition(pos).isPresent();
        this.resetHightlightedPositions();
        this.tilesOnBoard.stream().filter(x -> pieceMoves.contains(x.getBoardPosition()))
                .forEach(x -> x.highlightPosition(isPiecePresent.test(x.getBoardPosition())));
    }

    private BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {

        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        final double xMargin = this.localToScene(this.getBoundsInLocal()).getMinX();
        final double yMargin = this.localToScene(this.getBoundsInLocal()).getMinY();

        final int column = (int) (((x - xMargin)
                / (tile.getWidth() * this.getMatchController().getBoardStatus().getColumns()))
                * this.getMatchController().getBoardStatus().getColumns());

        int row = (int) (((y - yMargin) / (tile.getHeight() * this.getMatchController().getBoardStatus().getRows()))
                * this.getMatchController().getBoardStatus().getRows());

        if (this.isWhiteBottom) {
            row = this.getMatchController().getBoardStatus().getRows() - 1 - row;
        }

        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final int row = this.isWhiteBottom ? this.getMatchController().getBoardStatus().getRows() - 1 - position.getY()
                : position.getY();
        return new BoardPositionImpl(position.getX(), row);
    }

    private PieceRectangleImpl generateNewPiece(final Piece piece) {
        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(piece);

        pieceViewPort.setFill(new ImagePattern(this.imageLoader.getPieceImage(piece)));
        pieceViewPort.widthProperty().bind(tile.widthProperty().divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(tile.heightProperty().divide(PIECE_SCALE));

        pieceViewPort.setOnMousePressed(this::onPieceClick);
        pieceViewPort.setOnMouseDragged(this::onPieceDragged);
        pieceViewPort.setOnMouseReleased(this::onPieceReleased);

        return pieceViewPort;
    }

    private void addPieceToBoard(final PieceRectangleImpl piece) {
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.grid.add(piece, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(piece, HPos.CENTER);
    }

    private void drawPiece(final Piece piece) {
        final PieceRectangleImpl pieceViewPort = this.generateNewPiece(piece);
        this.pieces.add(pieceViewPort);
        this.addPieceToBoard(pieceViewPort);
    }

    private boolean isPieceMovable() {
        return !this.getMatchController().isInNavigationMode()
                && this.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE);
    }

    /**
     * Abort the movement. Put the piece in his original position.
     * 
     * @param piece
     */
    private void abortMove(final PieceRectangleImpl piece) {
        final BoardPosition realPiecePosition = this
                .getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.getChildren().remove(piece);
        this.grid.add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }

    public void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces);
        board.getPiecesStatus().forEach(this::drawPiece);
    }

    public MatchController getMatchController() {
        return (MatchController) this.matchView.getController();
    }
}
