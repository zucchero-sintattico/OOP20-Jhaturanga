package jhaturanga.views.editor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
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
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;
import jhaturanga.views.match.TileImpl;

public class EditorViewImpl extends AbstractView implements EditorView {

    @FXML
    private VBox whitePiecesSelector;

    @FXML
    private VBox blackPiecesSelector;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField columnsSelector;

    @FXML
    private TextField rowsSelector;

    @FXML
    private BorderPane grid;

    private static final double PIECE_SCALE = 1.5;
    private final Map<Rectangle, Piece> pieces = new HashMap<>();
    private final Map<Pair<PieceType, Player>, Image> piecesImage = new HashMap<>();
    private Player whitePlayer;
    private Player blackPlayer;
    private final GridPane guiBoard = new GridPane();
    private boolean isDeleting;

    @Override
    public final void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, this.getController().getModel().getFirstUser().get());
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, this.getController().getModel().getSecondUser().get());
        this.columnsSelector.setPromptText("COLUMNS");
        this.rowsSelector.setPromptText("ROWS");
        this.loadAllPieces();
        this.drawAllPieces();
        this.setupListeners();
        this.drawBoard(this.getEditorController().getBoardStatus());
        this.redraw(this.getEditorController().getBoardStatus());
        this.grid.setCenter(this.guiBoard);
        this.grid.prefWidthProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()));
    }

    @FXML
    public final void changeBoardDimensions(final Event event) {
        if (this.checkIfInputIsCorrect()) {
            this.getEditorController().resetBoard(Integer.parseInt(this.columnsSelector.getText()),
                    Integer.parseInt(this.rowsSelector.getText()));
            this.redraw(this.getEditorController().getBoardStatus());
            this.drawBoard(this.getEditorController().getBoardStatus());
        }
    };

    @FXML
    public final void backToMenu(final Event event) throws IOException {
        this.getEditorController().getModel().clearMatchInfo();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getEditorController().getModel());
    };

    @FXML
    public final void createBoard(final Event event) throws IOException {
        this.getEditorController().createCustomizedStartingBoard();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getEditorController().getModel());
    };

    private boolean checkIfInputIsCorrect() {
        try {
            Integer.parseInt(this.columnsSelector.getText());
            Integer.parseInt(this.rowsSelector.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void setupListeners() {
        this.pieces.keySet().forEach(i -> {
            i.setOnMousePressed(e -> this.onPieceClick(e, i));

            i.setOnMouseDragged(e -> this.onPieceDragged(e, i));

            i.setOnMouseReleased(e -> this.onPieceReleased(e, i));
        });

    }

    private void loadAllPieces() {
        List.of(this.whitePlayer, this.blackPlayer).forEach(x -> {
            Arrays.stream(PieceType.values()).forEach(i -> {
                final Rectangle pieceViewPort = new Rectangle();
                pieceViewPort.widthProperty().bind(this.whitePiecesSelector.widthProperty().divide(PIECE_SCALE));
                pieceViewPort.heightProperty().bind(this.whitePiecesSelector.widthProperty().divide(PIECE_SCALE));
                this.pieces.put(pieceViewPort, new PieceImpl(i, new BoardPositionImpl(0, 0), x));
            });
        });
    }

    private void drawAllPieces() {
        this.pieces.entrySet().forEach(i -> {
            final Image img = new Image(ClassLoader.getSystemResource(
                    "piece/PNGs/No_shadow/1024h/" + i.getValue().getPlayer().getColor().toString().charAt(0) + "_"
                            + i.getValue().getType().toString() + ".png")
                    .toString());
            i.getKey().setFill(new ImagePattern(img));
            this.piecesImage.put(new Pair<>(i.getValue().getType(), i.getValue().getPlayer()), img);
            if (i.getValue().getPlayer().getColor().equals(PlayerColor.WHITE)) {
                this.whitePiecesSelector.getChildren().add(i.getKey());
            } else {
                this.blackPiecesSelector.getChildren().add(i.getKey());
            }
        });
    }

    /**
     * Handler for the Click event on pieces.
     * 
     * @param event - the mouse event
     * @param piece - the piece clicked on
     */
    private void onPieceClick(final MouseEvent event, final Rectangle piece) {
        this.getStage().getScene().setCursor(Cursor.OPEN_HAND);
        // Check if it's over limit
        if (event.getButton().equals(MouseButton.SECONDARY) && isMouseOnBoard(event)) {
            this.removePieceTotally(piece);
        } else {
            if (this.guiBoard.getChildren().contains(piece)) {
                this.guiBoard.getChildren().remove(piece);
            } else {
                final Rectangle rect = new Rectangle();
                final Piece originalPiece = this.pieces.get(piece);
                rect.setFill(new ImagePattern(
                        this.piecesImage.get(new Pair<>(originalPiece.getType(), originalPiece.getPlayer()))));
                rect.widthProperty().bind(this.whitePiecesSelector.widthProperty().divide(PIECE_SCALE));
                rect.heightProperty().bind(this.whitePiecesSelector.widthProperty().divide(PIECE_SCALE));
                this.pieces.put(rect, new PieceImpl(originalPiece.getType(), originalPiece.getPiecePosition(),
                        originalPiece.getPlayer()));

                if (originalPiece.getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    final int pos = this.whitePiecesSelector.getChildren().indexOf(piece);
                    this.whitePiecesSelector.getChildren().remove(piece);
                    this.whitePiecesSelector.getChildren().add(pos, rect);
                    piece.setY(event.getSceneY()
                            - (this.whitePiecesSelector.getChildren().indexOf(rect) * piece.getHeight())
                            - (piece.getHeight() / 2));
                } else {
                    final int pos = this.blackPiecesSelector.getChildren().indexOf(piece);
                    this.blackPiecesSelector.getChildren().remove(piece);
                    this.blackPiecesSelector.getChildren().add(pos, rect);
                    piece.setY(event.getSceneY()
                            - (this.blackPiecesSelector.getChildren().indexOf(rect) * piece.getHeight())
                            - (piece.getHeight() / 2));
                }
                piece.setX(event.getSceneX() - (piece.getWidth() / 2));

                this.setupListeners();
            }
            this.root.getChildren().add(piece);
        }
    }

    /**
     * On piece dragged handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which is dragged
     */
    private void onPieceDragged(final MouseEvent event, final Rectangle piece) {
        this.getStage().getScene().setCursor(Cursor.CLOSED_HAND);
        piece.setX(event.getX() - piece.getWidth() / 2);
        piece.setY(event.getY() - piece.getHeight() / 2);

        if (event.getButton().equals(MouseButton.MIDDLE)) {
            this.drawPieceOnGuiBoard(event, piece);
            this.redraw(this.getEditorController().getBoardStatus());
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            this.pieces.entrySet().parallelStream()
                    .filter(i -> i.getValue().getPiecePosition()
                            .equals(this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY())))
                    .map(i -> i.getKey()).findAny().ifPresent(e -> {
                        this.removePieceTotally(e);
                        this.redraw(this.getEditorController().getBoardStatus());
                    });
        }
    }

    private void drawPieceOnGuiBoard(final MouseEvent event, final Rectangle piece) {
        if (this.root.getChildren().contains(piece) && this.isMouseOnBoard(event)) {
            final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(),
                    event.getSceneY());
            this.getEditorController().addPieceToBoard(this.pieces.get(piece).getPlayer().getPieceFactory()
                    .getPiece(this.pieces.get(piece).getType(), position));
            this.guiBoard.requestFocus();
        }
    }

    /**
     * On piece released handler.
     * 
     * @param event - the mouse event
     * @param piece - the piece which released.
     */
    private void onPieceReleased(final MouseEvent event, final Rectangle piece) {
        this.getStage().getScene().setCursor(Cursor.DEFAULT);
        this.updatePiecePositionOnGuiBoard(event, piece);
        this.redraw(this.getEditorController().getBoardStatus());
    }

    private void updatePiecePositionOnGuiBoard(final MouseEvent event, final Rectangle piece) {
        if (this.root.getChildren().contains(piece) && this.isMouseOnBoard(event)) {
            final BoardPosition position = this.getBoardPositionsFromGuiCoordinates(event.getSceneX(),
                    event.getSceneY());
            this.root.getChildren().remove(piece);
            this.getEditorController().updatePiecePosition(this.pieces.get(piece), position);
            this.getEditorController().addPieceToBoard(this.pieces.get(piece));
            this.guiBoard.requestFocus();
        } else if (!this.isMouseOnBoard(event)) {
            this.removePieceTotally(piece);
        }
    }

    private void removePieceTotally(final Rectangle piece) {
        this.getEditorController().removePieceAtPosition(this.pieces.get(piece).getPiecePosition());
        this.root.getChildren().remove(piece);
        this.guiBoard.getChildren().remove(piece);
        this.pieces.remove(piece);
    }

    private boolean isMouseOnBoard(final MouseEvent event) {
        return this.getEditorController().getModel().getEditor().getBoardStatus()
                .contains(this.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY()));
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();
        final TileImpl tile = this.guiBoard.getChildren().stream().filter(i -> i instanceof TileImpl)
                .map(i -> (TileImpl) i).findAny().get();
        pieceViewPort.setFill(new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer()))));
        pieceViewPort.widthProperty().bind(tile.widthProperty().divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(tile.heightProperty().divide(PIECE_SCALE));

        pieceViewPort.setOnMousePressed(e -> this.onPieceClick(e, pieceViewPort));

        pieceViewPort.setOnMouseDragged(e -> this.onPieceDragged(e, pieceViewPort));

        pieceViewPort.setOnMouseReleased(e -> this.onPieceReleased(e, pieceViewPort));

        this.pieces.put(pieceViewPort, piece);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());
        this.guiBoard.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void redraw(final Board board) {
        this.guiBoard.getChildren().removeAll(this.pieces.keySet());
        board.getBoardState().forEach(i -> this.drawPiece(i));
    }

    /**
     * Draw the board.
     * 
     * @param board - the board to be drew
     */
    private void drawBoard(final Board board) {
        this.guiBoard.getChildren().clear();
        final int bigger = Integer.max(board.getColumns(), board.getRows());
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                final TileImpl tile = new TileImpl(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.guiBoard.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.guiBoard.heightProperty().divide(bigger));
                this.guiBoard.add(tile, j, i);
            }
        }
    }

    private BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {
        final TileImpl tile = this.guiBoard.getChildren().stream().filter(i -> i instanceof TileImpl)
                .map(i -> (TileImpl) i).findAny().get();
        final int column = (int) (((x - this.guiBoard.getLayoutX())
                / (tile.getWidth() * this.getEditorController().getBoardStatus().getColumns()))
                * this.getEditorController().getBoardStatus().getColumns());
        final int row = this.getEditorController().getBoardStatus().getRows() - 1
                - (int) (((y - this.guiBoard.getLayoutY())
                        / (tile.getHeight() * this.getEditorController().getBoardStatus().getRows()))
                        * this.getEditorController().getBoardStatus().getRows());
        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.getEditorController().getBoardStatus().getRows() - 1 - position.getY());
    }

    @Override
    public final EditorController getEditorController() {
        return (EditorController) this.getController();
    }

}
