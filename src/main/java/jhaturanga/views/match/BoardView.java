package jhaturanga.views.match;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.game.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final MatchController matchController;
    private final GridPane grid;

    private boolean isPieceBeingDragged;

    private final Map<Rectangle, Piece> pieces = new HashMap<>();
    private Rectangle selectedRectangle;

    // TODO: implement image caching for quickly redraw
    private final Map<Pair<PieceType, Player>, Image> piecesImage;

    public BoardView(final MatchController matchController) {

        this.matchController = matchController;
        this.piecesImage = new HashMap<>();

        this.loadImages();

        this.grid = new GridPane();
        this.setupHistoryKeysHandler();
        this.getChildren().add(this.grid);

        this.drawBoard(this.matchController.getBoardStatus());

        this.redraw(this.matchController.getPrevBoard());
    }

    /*
     * Setup the handler for history navigation
     */
    private void setupHistoryKeysHandler() {
        this.grid.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.A)) {
                this.redraw(this.matchController.getPrevBoard());
                Sound.play(SoundsEnum.MOVE);
            } else if (e.getCode().equals(KeyCode.D)) {
                this.redraw(this.matchController.getNextBoard());
                Sound.play(SoundsEnum.MOVE);
            }
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
                final int i2 = i;
                final int j2 = j;
                final Pane tile = new Pane();
                tile.setOnMouseClicked(e -> {
                    if (e.getButton().equals(MouseButton.SECONDARY)) {
                        if ("-fx-background-color:#00FF00".equals(tile.getStyle())) {
                            tile.setStyle(
                                    (i2 + j2) % 2 == 0 ? "-fx-background-color:#CCC" : "-fx-background-color:#333");
                        } else {
                            tile.setStyle("-fx-background-color:#00FF00");
                        }
                    }
                });
                // TODO: adjust for style.
                tile.setStyle((i + j) % 2 == 0 ? "-fx-background-color:#CCC" : "-fx-background-color:#333");
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
        this.selectedRectangle = piece;
        if (this.grid.getChildren().contains(piece)) {
            this.grid.getChildren().remove(piece);
            this.getChildren().add(piece);
        }
    }

    private void onPieceDragged(final MouseEvent event, final Rectangle piece) {
        if (this.isPieceMovable()) {
            System.out.println("DRAG");
            this.isPieceBeingDragged = true;
            piece.setX(event.getX() - piece.getWidth() / 2);
            piece.setY(event.getY() - piece.getHeight() / 2);
        }
    }

    private void onPieceReleased(final MouseEvent event, final Rectangle piece) {
        System.out.println("RELEASE");

        final BoardPosition position = this.getBoardPositionsFromEvent(event);
        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;

            if ((event.getSceneX() - this.getLayoutX()) < 0 || (event.getSceneY() - this.getLayoutY()) < 0) {
                this.abortMove(piece);
                return;
            }

            final Piece movedPiece = this.pieces.get(this.selectedRectangle);

            if (this.matchController.move(movedPiece.getPiecePosition(), position)) {

                this.getChildren().remove(piece);
                this.grid.add(piece, realPosition.getX(), realPosition.getY());
                Sound.play(SoundsEnum.MOVE);

            } else {
                this.abortMove(piece);
            }

            this.grid.requestFocus();
            this.redraw(this.matchController.getBoardStatus());

        } else {

            this.getChildren().remove(piece);
            this.grid.add(piece, realPosition.getX(), realPosition.getY());

        }
    }

    private BoardPosition getBoardPositionsFromEvent(final MouseEvent event) {
        final int column = (int) (((event.getSceneX() - this.getLayoutX()) / this.grid.getWidth())
                * this.matchController.getBoardStatus().getColumns());
        final int row = this.matchController.getBoardStatus().getRows() - 1
                - (int) (((event.getSceneY() - this.getLayoutY()) / this.grid.getHeight())
                        * this.matchController.getBoardStatus().getRows());
        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.matchController.getBoardStatus().getRows() - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();

        pieceViewPort.setFill(new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer()))));
        pieceViewPort.widthProperty().bind(this.grid.widthProperty()
                .divide(this.matchController.getBoardStatus().getColumns()).divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(
                this.grid.heightProperty().divide(this.matchController.getBoardStatus().getRows()).divide(PIECE_SCALE));

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
                && !this.matchController.getModel().getActualMatch().get().isCompleted();
    }

    /**
     * Abort the movement. Put the piece in his original position.
     * 
     * @param piece
     */
    private void abortMove(final Rectangle piece) {
        this.getChildren().remove(piece);
        final BoardPosition piecePosition = this.pieces.get(piece).getPiecePosition();
        final BoardPosition realPiecePosition = this.getRealPositionFromBoardPosition(piecePosition);
        this.grid.add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }

    private void loadImages() {
        this.matchController.getBoardStatus().getBoardState().forEach(p -> {
            final Image img = new Image("file:" + ClassLoader.getSystemResource("piece/PNGs/No_shadow/1024h/"
                    + p.getPlayer().getColor().toString().charAt(0) + "_" + p.getType().toString() + ".png").getFile());
            this.piecesImage.put(new Pair<>(p.getType(), p.getPlayer()), img);
        });
    }

    public void redraw(final Board board) {
        final var toRemove = this.grid.getChildren().stream().filter(n -> n instanceof Rectangle)
                .collect(Collectors.toList());
        this.grid.getChildren().removeAll(toRemove);
        // this.piecesPosition.clear();

        board.getBoardState().forEach(i -> this.drawPiece(i));
    }

}
