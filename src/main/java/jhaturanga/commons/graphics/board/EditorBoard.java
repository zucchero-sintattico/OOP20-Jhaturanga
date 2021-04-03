package jhaturanga.commons.graphics.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.util.Pair;
import jhaturanga.commons.graphics.components.PieceRectangle;
import jhaturanga.commons.graphics.components.Tile;
import jhaturanga.controllers.editor.EditorController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public final class EditorBoard extends GraphicalBoard {

    private final GridPane guiBoard = new GridPane();
    private final Map<PlayerColor, VBox> pieceSelectors;
    private static final double PIECE_SCALE = 1.5;
    private final Set<PieceRectangle> pieces = new HashSet<>();
    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage = new HashMap<>();
    private final EditorController editorController;

    private final Supplier<Tile> tileSampleSupplierForBindings = () -> this.guiBoard.getChildren().stream()
            .filter(e -> e instanceof Tile).map(e -> (Tile) e).findAny().get();

    public EditorBoard(final EditorController editorController, final VBox whitePieceSelector,
            final VBox blackPieceSelector) {

        super(editorController.getBoardStatus().getRows(), editorController.getBoardStatus().getColumns());
        this.pieceSelectors = Map.of(PlayerColor.WHITE, whitePieceSelector, PlayerColor.BLACK, blackPieceSelector);
        this.editorController = editorController;
        this.createBoard();
        this.redraw(this.editorController.getBoardStatus());
        this.getChildren().add(this.guiBoard);
    }

    private void createNodeBindings(final PieceRectangle pieceRect, final Image img, final Pane binder) {
        pieceRect.widthProperty().bind(binder.widthProperty().divide(PIECE_SCALE));
        pieceRect.heightProperty().bind(binder.widthProperty().divide(PIECE_SCALE));
        this.pieces.add(pieceRect);
        pieceRect.setFill(new ImagePattern(img));
    }

    public void generateNewPieceViewPort(final MouseEvent event, final PieceRectangle pieceRect) {
        final PieceRectangle newPieceRect = new PieceRectangle(new PieceImpl(pieceRect.getPiece()));
        this.createNodeBindings(newPieceRect,
                this.piecesImage.get(new Pair<>(newPieceRect.getPieceType(), newPieceRect.getPieceColor())),
                this.tileSampleSupplierForBindings.get());
        this.pieces.add(newPieceRect);
        this.rearrangeVBox(this.pieceSelectors.get(pieceRect.getPieceColor()), pieceRect, newPieceRect, event);
    }

    private void rearrangeVBox(final VBox vBoxSelector, final PieceRectangle pieceRect,
            final PieceRectangle newPieceRect, final MouseEvent event) {
        final int pos = vBoxSelector.getChildren().indexOf(pieceRect);
        vBoxSelector.getChildren().remove(pieceRect);
        vBoxSelector.getChildren().add(pos, newPieceRect);
        pieceRect.setY(event.getSceneY()
                - (this.pieceSelectors.get(pieceRect.getPieceColor()).getChildren().indexOf(newPieceRect)
                        * pieceRect.getHeight())
                - (pieceRect.getHeight() / 2));
        pieceRect.setX(event.getSceneX() - (pieceRect.getWidth() / 2));
    }

    public void drawPieceOnGuiBoard(final MouseEvent event, final PieceRectangle piece) {
        if (this.getChildren().contains(piece) && this.isMouseOnBoard(event)) {
            final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(),
                    event.getSceneY());
            this.editorController.addPieceToBoard(piece.getPiece().getPlayer().getPieceFactory()
                    .getPieceFromPieceType(piece.getPieceType(), position));
            this.guiBoard.requestFocus();
        }
    }

    public void updatePiecePositionOnGuiBoard(final MouseEvent event, final PieceRectangle piece) {
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

    public void removePieceTotally(final PieceRectangle piece) {
        this.editorController.removePieceAtPosition(piece.getPiece().getPiecePosition());
        this.getChildren().remove(piece);
        this.guiBoard.getChildren().remove(piece);
        this.pieces.remove(piece);
    }

    public boolean isMouseOnBoard(final MouseEvent event) {
        return this.editorController.getBoardStatus()
                .contains(this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY()));
    }

    public BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {

        final Tile tile = this.getGrid().getChildren().stream().filter(i -> i instanceof Tile).map(i -> (Tile) i)
                .findAny().get();

        final double xMargin = this.localToScene(this.getBoundsInLocal()).getMinX();
        final double yMargin = this.localToScene(this.getBoundsInLocal()).getMinY();

        final int column = (int) ((((x - xMargin) / this.getScene().getRoot().getScaleX())
                / (tile.getWidth() * this.editorController.getBoardStatus().getColumns()))
                * this.editorController.getBoardStatus().getColumns());

        int row = (int) ((((y - yMargin) / this.getScene().getRoot().getScaleY())
                / (tile.getHeight() * this.editorController.getBoardStatus().getRows()))
                * this.editorController.getBoardStatus().getRows());
        row = this.editorController.getBoardStatus().getRows() - 1 - row;

        return new BoardPositionImpl(column, row);
    }
}
