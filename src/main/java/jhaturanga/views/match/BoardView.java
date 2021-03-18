package jhaturanga.views.match;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import javafx.util.Pair;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final MatchController matchController;
    private final GridPane grid = new GridPane();

    private final Map<Rectangle, Piece> pieces = new HashMap<>();
    private Rectangle selectedRectangle;
    private boolean isPieceBeingDragged;

    private final Set<TileImpl> tilesHighlighted = new HashSet<>();
    private final MatchView matchView;

    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage;

    public BoardView(final MatchController matchController, final MatchView matchView) {
        this.matchView = matchView;
        this.matchController = matchController;
        this.piecesImage = new HashMap<>();
        this.loadImages();
        this.setupHistoryKeysHandler();

        this.getChildren().add(this.grid);
        this.drawBoard(this.matchController.getBoardStatus());
        this.redraw(this.matchController.getBoardStatus());
        Platform.runLater(() -> this.grid.requestFocus());
    }

    /*
     * Setup the handler for history navigation
     */
    private void setupHistoryKeysHandler() {
        this.grid.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.A) && this.selectedRectangle == null) {
                this.resetHighlightedTiles();
                final Optional<Board> board = this.matchController.getPrevBoard();
                if (board.isPresent()) {
                    this.redraw(board.get());
                    Sound.play(SoundsEnum.MOVE);
                }
            } else if (e.getCode().equals(KeyCode.D) && this.selectedRectangle == null) {
                this.resetHighlightedTiles();
                final Optional<Board> board = this.matchController.getNextBoard();
                if (board.isPresent()) {
                    this.redraw(board.get());
                    Sound.play(SoundsEnum.MOVE);
                }
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
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                final TileImpl tile = new TileImpl(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));
                this.grid.add(tile, j, i);
            }
        }
    }

    /**
     * Piece Click Handler. This should hint all the position where the piece can
     * go.
     */
    private void onPieceClick(final Rectangle piece) {
        this.matchView.getStage().getScene().setCursor(Cursor.OPEN_HAND);
        this.selectedRectangle = piece;
        if (this.grid.getChildren().contains(piece)) {
            this.grid.getChildren().remove(piece);
            this.getChildren().add(piece);
        }
        if (this.matchController.getPlayerTurn().equals(this.pieces.get(piece).getPlayer()) && this.isPieceMovable()) {
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
    private void onPieceReleased(final MouseEvent event, final Rectangle piece) {
        this.matchView.getStage().getScene().setCursor(Cursor.DEFAULT);
        this.selectedRectangle = null;
        final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY());
        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;

            // Check if it's over limit
            if ((event.getSceneX() - this.getLayoutX()) < 0 || (event.getSceneY() - this.getLayoutY()) < 0) {
                this.abortMove(piece);
                return;
            }
            // Get the piece moved
            final Piece movedPiece = this.pieces.get(piece);
            // Check if the engine accept the movement
            final MovementResult result = this.matchController.move(movedPiece.getPiecePosition(), position);

            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.getChildren().remove(piece);
                this.grid.add(piece, realPosition.getX(), realPosition.getY());
                Sound.play(SoundsEnum.valueOf(result.toString()));
            } else {
                this.abortMove(piece);
            }

            this.grid.requestFocus();
            this.redraw(this.matchController.getBoardStatus());
            this.resetHighlightedTiles();
            this.checkMatchStatus();
        } else {
            this.abortMove(piece);
        }
    }

    private void checkMatchStatus() {
        if (!this.matchController.matchStatus().equals(MatchStatusEnum.ACTIVE)) {
            try {
                this.matchController.saveMatch();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.matchController.getModel().clearMatchInfo();
            Platform.runLater(() -> {
                final EndGamePopup popup = new EndGamePopup();
                popup.setMessage("Game ended for " + this.matchController.matchStatus().toString());
                popup.setButtonAction(() -> {

                    PageLoader.switchPage(this.matchView.getStage(), Pages.HOME,
                            this.matchView.getController().getModel());

                    popup.close();
                });
                popup.show();
            });
        }
    }

    private void drawPossibleDestinations(final Rectangle piece) {
        this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i).forEach(i -> {
            if (this.matchController.getPiecePossibleMoves(this.pieces.get(piece)).contains(i.getBoardPosition())) {
                this.tilesHighlighted.add(i);
                i.addCircleHighlight(new CircleHighlightImpl(i,
                        this.matchController.getBoardStatus().getPieceAtPosition(i.getBoardPosition()).isPresent()));
            }
        });
    }

    private BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {
        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        final int column = (int) (((x - this.getLayoutX())
                / (tile.getWidth() * this.matchController.getBoardStatus().getColumns()))
                * this.matchController.getBoardStatus().getColumns());
        final int row = this.matchController.getBoardStatus().getRows() - 1
                - (int) (((y - this.getLayoutY())
                        / (tile.getHeight() * this.matchController.getBoardStatus().getRows()))
                        * this.matchController.getBoardStatus().getRows());
        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.matchController.getBoardStatus().getRows() - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();

        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();
        pieceViewPort.setFill(
                new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer().getColor()))));
        pieceViewPort.widthProperty().bind(tile.widthProperty().divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(tile.heightProperty().divide(PIECE_SCALE));

        /*
         * When a piece is pressed we save the selected rectangle and make a call to the
         * onPieceClick function.
         */
        pieceViewPort.setOnMousePressed(e -> this.onPieceClick(pieceViewPort));

        /**
         * Handler for make the piece draggable over the board.
         */
        pieceViewPort.setOnMouseDragged(e -> this.onPieceDragged(e, pieceViewPort));

        pieceViewPort.setOnMouseReleased(e -> this.onPieceReleased(e, pieceViewPort));

        this.pieces.put(pieceViewPort, piece);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());
        this.grid.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private boolean isPieceMovable() {
        return !this.matchController.isInNavigationMode()
                && this.matchController.matchStatus().equals(MatchStatusEnum.ACTIVE);
    }

    /**
     * Abort the movement. Put the piece in his original position.
     * 
     * @param piece
     */
    private void abortMove(final Rectangle piece) {
        final BoardPosition piecePosition = this.pieces.get(piece).getPiecePosition();
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
        List.of(this.matchController.getModel().getWhitePlayer(), this.matchController.getModel().getBlackPlayer())
                .stream().forEach(x -> {
                    Arrays.stream(PieceType.values()).forEach(i -> {
                        final Image img = new Image(
                                ClassLoader
                                        .getSystemResource("piece/PNGs/No_shadow/1024h/"
                                                + x.getColor().toString().charAt(0) + "_" + i.toString() + ".png")
                                        .toString());
                        this.piecesImage.put(new Pair<>(i, x.getColor()), img);
                    });
                });
    }

    private void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces.keySet());
        board.getBoardState().forEach(i -> this.drawPiece(i));
    }

}
