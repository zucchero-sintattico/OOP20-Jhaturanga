package jhaturanga.views.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import jhaturanga.controllers.editor.EditorController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.views.match.TileImpl;

public class EditorBoardView extends Pane {

    private final GridPane guiBoard = new GridPane();
    private final Map<PlayerColor, VBox> pieceSelectors;
    private static final BoardPosition STARTING_DEFAULT_BOARD_POS = new BoardPositionImpl(0, 0);
    private static final double PIECE_SCALE = 1.5;
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage = new HashMap<>();
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final EditorController editorController;
    private final EditorView editorView;
    /**
     * Because of bindings I need to always have a new instance of a whatever Tile
     * on the Board, which one is not important because their size is consistent
     * between them. Apparently, Observable properties do not do what I thought they
     * where supposed to do, or at least, they do not always act as I imagine they
     * should. Long story short: I found it easier to just have a Supplier that
     * returns a single updated TileImpl, even if this means opening a Stream on the
     * GridPane's children just to get a single Object.
     */
    private final Supplier<TileImpl> tileSupplierForBindings = () -> this.guiBoard.getChildren().stream()
            .filter(e -> e instanceof TileImpl).map(e -> (TileImpl) e).findAny().get();

    public EditorBoardView(final EditorController editorController, final EditorView editorView,
            final VBox whitePieceSelector, final VBox blackPieceSelector) {
        this.pieceSelectors = Map.of(PlayerColor.WHITE, whitePieceSelector, PlayerColor.BLACK, blackPieceSelector);
        this.editorView = editorView;
        this.editorController = editorController;
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.editorController.getModel().getFirstUser().get());
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.editorController.getModel().getSecondUser().get());
        this.drawBoard(this.editorController.getBoardStatus());
        this.loadAllPieces();
        this.redraw(this.editorController.getBoardStatus());
        this.getChildren().add(this.guiBoard);
        this.pieces.forEach(this::setPieceListeners);
    }

    private void createNodeBindings(final PieceRectangleImpl pieceRect, final Image img, final Pane binder) {
        pieceRect.widthProperty().bind(binder.widthProperty().divide(PIECE_SCALE));
        pieceRect.heightProperty().bind(binder.widthProperty().divide(PIECE_SCALE));
        this.pieces.add(pieceRect);
        pieceRect.setFill(new ImagePattern(img));
    }

    private void setPieceListeners(final PieceRectangleImpl pieceRect) {
        pieceRect.setOnMousePressed(e -> this.onPieceClick(e, pieceRect));
        pieceRect.setOnMouseDragged(e -> this.onPieceDragged(e, pieceRect));
        pieceRect.setOnMouseReleased(e -> this.onPieceReleased(e, pieceRect));
    }

    /**
     * This method is called only at the init to, it is used to save and load all
     * images, so that during the board editing images are already "cached" and can
     * be accessed with a Map.
     */
    private void loadAllPieces() {
        List.of(this.whitePlayer, this.blackPlayer).forEach(player -> {
            Arrays.stream(PieceType.values()).forEach(pieceType -> {
                final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(
                        new PieceImpl(pieceType, STARTING_DEFAULT_BOARD_POS, player));
                final Image img = new Image(ClassLoader.getSystemResource(
                        "piece/PNGs/No_shadow/1024h/" + pieceViewPort.getPieceColor().toString().charAt(0) + "_"
                                + pieceViewPort.getPieceType().toString() + ".png")
                        .toString());
                this.createNodeBindings(pieceViewPort, img, this.pieceSelectors.get(player.getColor()));
                this.piecesImage.put(new Pair<>(pieceType, player.getColor()), img);
                this.pieceSelectors.get(player.getColor()).getChildren().add(pieceViewPort);
            });
        });
    }

    /**
     * Handler for the Click event on pieces.
     * 
     * @param event     - the mouse event
     * @param pieceRect - the piece clicked on
     */
    private void onPieceClick(final MouseEvent event, final PieceRectangleImpl pieceRect) {
        this.editorView.getStage().getScene().setCursor(Cursor.OPEN_HAND);
        // Check if it's over limit
        if (event.getButton().equals(MouseButton.SECONDARY) && isMouseOnBoard(event)) {
            this.removePieceTotally(pieceRect);
        } else {
            if (this.guiBoard.getChildren().contains(pieceRect)) {
                if (event.getButton().equals(MouseButton.MIDDLE)) {
                    this.removePieceTotally(pieceRect);
                    this.drawPieceOnGuiBoard(event, pieceRect);
                } else {
                    this.guiBoard.getChildren().remove(pieceRect);
                }
            } else {
                this.generateNewPieceViewPort(event, pieceRect);
            }
            this.getChildren().add(pieceRect);
        }
    }

    private void generateNewPieceViewPort(final MouseEvent event, final PieceRectangleImpl pieceRect) {
        final PieceRectangleImpl newPieceRect = new PieceRectangleImpl(new PieceImpl(pieceRect.getPiece()));
        this.createNodeBindings(newPieceRect,
                this.piecesImage.get(new Pair<>(newPieceRect.getPieceType(), newPieceRect.getPieceColor())),
                this.pieceSelectors.get(pieceRect.getPieceColor()));
        this.pieces.add(newPieceRect);
        this.rearrangeVBox(this.pieceSelectors.get(pieceRect.getPieceColor()), pieceRect, newPieceRect, event);
        this.setPieceListeners(newPieceRect);
    }

    private void rearrangeVBox(final VBox vBoxSelector, final PieceRectangleImpl pieceRect,
            final PieceRectangleImpl newPieceRect, final MouseEvent event) {
        final int pos = vBoxSelector.getChildren().indexOf(pieceRect);
        vBoxSelector.getChildren().remove(pieceRect);
        vBoxSelector.getChildren().add(pos, newPieceRect);
        pieceRect.setY(event.getSceneY()
                - (this.pieceSelectors.get(pieceRect.getPieceColor()).getChildren().indexOf(newPieceRect)
                        * pieceRect.getHeight())
                - (pieceRect.getHeight() / 2));
        pieceRect.setX(event.getSceneX() - (pieceRect.getWidth() / 2));
    }

    /**
     * On piece dragged handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which is dragged
     */
    private void onPieceDragged(final MouseEvent event, final PieceRectangleImpl piece) {
        this.editorView.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
        piece.setX(event.getX() - piece.getWidth() / 2);
        piece.setY(event.getY() - piece.getHeight() / 2);
        if (event.getButton().equals(MouseButton.MIDDLE)) {
            this.drawPieceOnGuiBoard(event, piece);
            this.redraw(this.editorController.getBoardStatus());
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            this.pieces.stream()
                    .filter(i -> i.getPiece().getPiecePosition()
                            .equals(this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY())))
                    .findAny().ifPresent(e -> {
                        this.removePieceTotally(e);
                        this.redraw(this.editorController.getBoardStatus());
                    });
        }
    }

    private void drawPieceOnGuiBoard(final MouseEvent event, final PieceRectangleImpl piece) {
        if (this.getChildren().contains(piece) && this.isMouseOnBoard(event)) {
            final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(),
                    event.getSceneY());
            this.editorController.addPieceToBoard(
                    piece.getPiece().getPlayer().getPieceFactory().getPieceFromPieceType(piece.getPieceType(), position));
            this.guiBoard.requestFocus();
        }
    }

    /**
     * On piece released handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which released.
     */
    private void onPieceReleased(final MouseEvent event, final PieceRectangleImpl piece) {
        this.editorView.getStage().getScene().setCursor(Cursor.DEFAULT);
        this.updatePiecePositionOnGuiBoard(event, piece);
        this.redraw(this.editorController.getBoardStatus());
    }

    private void updatePiecePositionOnGuiBoard(final MouseEvent event, final PieceRectangleImpl piece) {
        if (this.getChildren().contains(piece) && this.isMouseOnBoard(event)) {

            final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(),
                    event.getSceneY());
            this.getChildren().remove(piece);
            piece.getPiece().setPosition(position);
            this.editorController.addPieceToBoard(piece.getPiece());

        } else if (!this.isMouseOnBoard(event)) {
            this.removePieceTotally(piece);
        }
    }

    private void removePieceTotally(final PieceRectangleImpl piece) {
        this.editorController.removePieceAtPosition(piece.getPiece().getPiecePosition());
        this.getChildren().remove(piece);
        this.guiBoard.getChildren().remove(piece);
        this.pieces.remove(piece);
    }

    private boolean isMouseOnBoard(final MouseEvent event) {
        return this.editorController.getBoardStatus()
                .contains(this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY()));
    }

    private void drawPiece(final Piece piece) {
        final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(piece);
        this.createNodeBindings(pieceViewPort,
                this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer().getColor())),
                this.tileSupplierForBindings.get());
        this.setPieceListeners(pieceViewPort);
        this.pieces.add(pieceViewPort);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());

        this.guiBoard.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void redraw(final Board board) {
        this.guiBoard.getChildren().removeAll(this.pieces);
        board.getBoardState().forEach(this::drawPiece);
    }

    /**
     * Draw the board.
     * 
     * @param board - the board to be drew
     */
    public void drawBoard(final Board board) {
        this.guiBoard.getChildren().clear();
        final int bigger = Integer.max(board.getColumns(), board.getRows());
        Stream.iterate(0, i -> i + 1).limit(board.getRows()).forEach(i -> {
            Stream.iterate(0, j -> j + 1).limit(board.getColumns()).forEach(j -> {
                final TileImpl tile = new TileImpl(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));
                this.guiBoard.add(tile, j, i);
            });
        });
    }

    private BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {
        final TileImpl tile = tileSupplierForBindings.get();

        final int column = (int) (((x - this.getLayoutX()) / (tile.getWidth())));

        final int row = this.editorController.getBoardStatus().getRows() - 1
                - (int) (((y - this.getLayoutY()) / (tile.getHeight())));

        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.editorController.getBoardStatus().getRows() - 1 - position.getY());
    }

}
