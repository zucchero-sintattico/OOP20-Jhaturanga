package jhaturanga.commons.graphics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jhaturanga.commons.Pair;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.views.editor.PieceRectangleImpl;
import jhaturanga.views.match.MatchView;

public final class MatchBoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final GridPane grid = new GridPane();
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage = new HashMap<>();
    private final Set<TileImpl> tilesHighlighted = new HashSet<>();
    private final Set<TileImpl> tilesOnBoard = new HashSet<>();
    private boolean isOnePieceSelected;
    private boolean isPieceBeingDragged;
    private final MatchView matchView;

    private final Runnable onMatchFinish;

    private final Function<Predicate<BoardPosition>, Set<TileImpl>> getTilesThatRespectPredicate = (
            predicate) -> this.tilesOnBoard.stream().filter(e -> predicate.test(e.getBoardPosition()))
                    .collect(Collectors.toSet());

    public MatchBoardView(final MatchView matchView, final Runnable onMatchFinish) {

        this.matchView = matchView;
        this.onMatchFinish = onMatchFinish;

        this.loadImages();
        this.setupHistoryKeysHandler();
        this.getChildren().add(this.grid);
        this.drawBoard(this.getMatchController().getBoardStatus());
        this.redraw(this.getMatchController().getBoardStatus());

        Platform.runLater(() -> this.grid.requestFocus());
    }

    /*
     * Setup the handler for history navigation
     */
    private void setupHistoryKeysHandler() {
        this.grid.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.A) && !this.isOnePieceSelected) {
                this.resetHighlightedTiles();
                this.getMatchController().getPrevBoard().ifPresent(board -> {
                    this.redraw(board);
                    Sound.play(SoundsEnum.MOVE);
                });

            } else if (e.getCode().equals(KeyCode.D) && !this.isOnePieceSelected) {
                this.resetHighlightedTiles();
                this.getMatchController().getNextBoard().ifPresent(board -> {
                    this.redraw(board);
                    Sound.play(SoundsEnum.MOVE);
                });
            }
            e.consume();
            this.grid.requestFocus();
        });
    }

    /**
     * Draw the board.
     * 
     * @param board - the board to be drew
     */
    private void drawBoard(final Board board) {
        final int bigger = Integer.max(board.getColumns(), board.getRows());

        IntStream.range(0, board.getRows()).forEach(i -> {
            IntStream.range(0, board.getColumns()).forEach(j -> {
                final TileImpl tile = new TileImpl(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));
                this.tilesOnBoard.add(tile);
                this.grid.add(tile, j, i);
            });
        });
    }

    /**
     * Piece Click Handler. This should hint all the position where the piece can
     * go.
     */
    private void onPieceClick(final PieceRectangleImpl piece) {
        this.matchView.getStage().getScene().setCursor(Cursor.OPEN_HAND);
        this.isOnePieceSelected = true;
        if (this.grid.getChildren().contains(piece)) {
            this.grid.getChildren().remove(piece);
            this.getChildren().add(piece);
        }
        if (this.getMatchController().getPlayerTurn().equals(piece.getPiece().getPlayer()) && this.isPieceMovable()) {
            this.resetHighlightedTiles();
            this.drawPossibleDestinations(piece);
        }
    }

    private void resetHighlightedTiles() {
        this.tilesHighlighted.forEach(i -> {
            i.resetCircleHighlight();
            i.getChildren().clear();
        });
        this.tilesHighlighted.clear();
    }

    private void resetMovementHighlight() {
        this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .forEach(TileImpl::resetMovementHighlight);
    }

    /**
     * On piece dragged handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which is dragged
     */
    private void onPieceDragged(final MouseEvent event, final Rectangle piece) {
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
    private void onPieceReleased(final MouseEvent event, final PieceRectangleImpl piece) {

        this.matchView.getStage().getScene().setCursor(Cursor.DEFAULT);
        this.isOnePieceSelected = false;

        final BoardPosition startingPos = piece.getPiece().getPiecePosition();
        final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY());
        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;
            // Get the piece moved
            final Piece movedPiece = piece.getPiece();
            // Check if the engine accept the movement
            final MovementResult result = this.getMatchController().move(movedPiece.getPiecePosition(), position);

            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.getChildren().remove(piece);
                this.grid.add(piece, realPosition.getX(), realPosition.getY());
                this.redraw(this.getMatchController().getBoardStatus());
                this.resetMovementHighlight();

                this.getTilesThatRespectPredicate.apply(x -> x.equals(position) || x.equals(startingPos))
                        .forEach(TileImpl::highlightMovement);

                Sound.play(SoundsEnum.valueOf(result.toString()));
            } else {
                this.abortMove(piece);
            }

            this.grid.requestFocus();
            this.redraw(this.getMatchController().getBoardStatus());
            this.resetHighlightedTiles();
            this.checkMatchStatus();
        } else {
            this.abortMove(piece);
        }
    }

    private void checkMatchStatus() {
        if (!this.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE)) {
            this.onMatchFinish.run();
        }
    }

    private void drawPossibleDestinations(final PieceRectangleImpl piece) {

        // I need to save the possible moves here to avoid recalculating them each time
        // the predicate is tested in the Function.
        final Set<BoardPosition> pieceMoves = this.getMatchController().getPiecePossibleMoves(piece.getPiece());

        this.getTilesThatRespectPredicate.apply(tilePos -> pieceMoves.contains(tilePos)).forEach(tile -> {
            this.tilesHighlighted.add(tile);
            tile.addCircleHighlight(new CircleHighlightImpl(tile, this.getMatchController().getBoardStatus()
                    .getPieceAtPosition(tile.getBoardPosition()).isPresent()));

        });
    }

    private BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {

        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        final double xMargin = this.localToScene(this.getBoundsInLocal()).getMinX();
        final double yMargin = this.localToScene(this.getBoundsInLocal()).getMinY();

        final int column = (int) (((x - xMargin)
                / (tile.getWidth() * this.getMatchController().getBoardStatus().getColumns()))
                * this.getMatchController().getBoardStatus().getColumns());

        final int row = this.getMatchController().getBoardStatus().getRows() - 1
                - (int) (((y - yMargin) / (tile.getHeight() * this.getMatchController().getBoardStatus().getRows()))
                        * this.getMatchController().getBoardStatus().getRows());

        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.getMatchController().getBoardStatus().getRows() - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {

        final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(piece);

        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        pieceViewPort.setFill(
                new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer().getColor()))));
        pieceViewPort.widthProperty().bind(tile.widthProperty().divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(tile.heightProperty().divide(PIECE_SCALE));

        pieceViewPort.setOnMousePressed(e -> this.onPieceClick(pieceViewPort));

        pieceViewPort.setOnMouseDragged(e -> this.onPieceDragged(e, pieceViewPort));

        pieceViewPort.setOnMouseReleased(e -> this.onPieceReleased(e, pieceViewPort));

        this.pieces.add(pieceViewPort);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());
        this.grid.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
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
        final BoardPosition piecePosition = piece.getPiece().getPiecePosition();
        final BoardPosition realPiecePosition = this.getRealPositionFromBoardPosition(piecePosition);
        this.getChildren().remove(piece);
        this.grid.add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }

    /**
     * All images representing all the Pieces of every color must be loaded at the
     * start and creation of the board. This is done to improve performances. Also,
     * we cannot generate only the images from the starting board, because some
     * PieceTypes might not be present at the start of the game but may be needed in
     * a second moment. So all images must be loaded.
     */
    private void loadImages() {

        List.of(this.getMatchController().getWhitePlayer(), this.getMatchController().getBlackPlayer()).stream()
                .forEach(player -> {
                    Arrays.stream(PieceType.values()).forEach(pieceType -> {
                        final Image img = new Image(ClassLoader
                                .getSystemResource("piece/PNGs/No_shadow/1024h/"
                                        + player.getColor().toString().charAt(0) + "_" + pieceType.toString() + ".png")
                                .toString());
                        this.piecesImage.put(new Pair<>(pieceType, player.getColor()), img);
                    });
                });
    }

    private void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces);
        board.getBoardState().forEach(this::drawPiece);
    }

    private MatchController getMatchController() {
        return this.matchView.getMatchController();
    }
}
