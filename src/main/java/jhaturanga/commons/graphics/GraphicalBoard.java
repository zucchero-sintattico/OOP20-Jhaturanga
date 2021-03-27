package jhaturanga.commons.graphics;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.editor.PieceRectangleImpl;

public class GraphicalBoard extends Pane {

    private static final double PIECE_SCALE = 2;

    private final GraphicPieceMovementStrategy pieceMovementStrategy;
    private final PieceImageLoader imageLoader = new PieceImageLoader();
    private final DoubleBinding tileDimension;

    private final GridPane grid = new GridPane();
    private final Set<PieceRectangleImpl> pieces = new HashSet<>();
    private final Set<TileImpl> tilesOnBoard = new HashSet<>();

    public GraphicalBoard(final GraphicPieceMovementStrategy pieceMovementStrategy, final int rows, final int columns) {
        this.pieceMovementStrategy = pieceMovementStrategy;
        this.tileDimension = this.widthProperty().divide(Math.max(rows, columns));
        this.getChildren().add(grid);
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
     * @param board
     */
    public void drawBoard(final Board board) {
        IntStream.range(0, board.getRows()).forEach(row -> {
            IntStream.range(0, board.getColumns()).forEach(col -> {
                final TileImpl tile = new TileImpl(
                        this.getGridCoordinateFromBoardPosition(new BoardPositionImpl(col, row)), this.tileDimension,
                        board.getRows());
                this.tilesOnBoard.add(tile);
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
        return new BoardPositionImpl(position.getX(), position.getY());
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

}
