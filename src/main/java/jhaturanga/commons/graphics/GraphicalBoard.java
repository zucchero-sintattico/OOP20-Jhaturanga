package jhaturanga.commons.graphics;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.editor.PieceRectangleImpl;

public class GraphicalBoard extends Pane {

    private static final double PIECE_SCALE = 2;

    private GraphicPieceMovementStrategy pieceMovementStrategy;
    private DoubleBinding tileDimension;
    private final PieceImageLoader imageLoader = new PieceImageLoader();

    private final GridPane grid = new GridPane();
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Set<TileImpl> tiles = new HashSet<>();
    private final int rows;
    private final int columns;

    public GraphicalBoard(final int rows, final int columns) {
        this(rows, columns, null);
    }

    public GraphicalBoard(final int rows, final int columns, final GraphicPieceMovementStrategy pieceMovementStrategy) {
        this.pieceMovementStrategy = pieceMovementStrategy;
        this.tileDimension = this.widthProperty().divide(Math.max(rows, columns));
        this.getChildren().add(this.grid);
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * 
     * @param tileDimension
     */
    public void setTileDimension(final DoubleBinding tileDimension) {
        this.tileDimension = tileDimension;
    }

    /**
     * 
     * @param pieceMovementStrategy
     */
    public void setPieceMovementStrategy(final GraphicPieceMovementStrategy pieceMovementStrategy) {
        this.pieceMovementStrategy = pieceMovementStrategy;
    }

    /**
     * 
     * @return the grid
     */
    public GridPane getGrid() {
        return this.grid;
    }

    /**
     * 
     * @return the pieces
     */
    public Set<PieceRectangleImpl> getPieces() {
        return this.pieces;
    }

    /**
     * 
     * @return the tiles
     */
    public Set<TileImpl> getTiles() {
        return this.tiles;
    }

    /**
     * 
     * @param board
     */
    public void drawBoard(final Board board) {
        IntStream.range(0, board.getRows()).forEach(row -> {
            IntStream.range(0, board.getColumns()).forEach(col -> {
                final TileImpl tile = new TileImpl(
                        this.getGridCoordinateFromBoardPosition(new BoardPositionImpl(col, row)), this.tileDimension,
                        board.getRows());
                this.tiles.add(tile);
                this.grid.add(tile, col, row);
            });
        });
    }

    /**
     * 
     * @param piece
     */
    public void addPieceToBoard(final PieceRectangleImpl piece) {
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.grid.add(piece, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(piece, HPos.CENTER);
    }

    /**
     * 
     * @param position
     * @return the board coordinate
     */
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(), this.rows - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {
        final PieceRectangleImpl pieceViewPort = new PieceRectangleImpl(piece, this.imageLoader.getPieceImage(piece),
                this.tileDimension.divide(PIECE_SCALE), this.pieceMovementStrategy);
        this.pieces.add(pieceViewPort);
        this.addPieceToBoard(pieceViewPort);
    }

    /**
     * 
     * @param board
     */
    public void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces);
        board.getPiecesStatus().forEach(this::drawPiece);
    }

    /**
     * 
     * @param movementResult
     */
    public void playSound(final MovementResult movementResult) {
        Sound.play(SoundsEnum.fromMovementResult(movementResult));
    }

}
