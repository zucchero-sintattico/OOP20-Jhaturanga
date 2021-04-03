package jhaturanga.commons.graphics.board;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jhaturanga.commons.graphics.components.PieceImageLoader;
import jhaturanga.commons.graphics.components.PieceRectangle;
import jhaturanga.commons.graphics.components.Tile;
import jhaturanga.commons.graphics.strategy.history.HistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.history.NonNavigableHistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.movement.GraphicPieceMovementStrategy;
import jhaturanga.commons.graphics.strategy.movement.NonMovableGraphicPieceMovementStrategy;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public class GraphicalBoard extends Pane {

    private static final double PIECE_SCALE = 2;

    private final PieceImageLoader imageLoader = new PieceImageLoader();

    private GraphicPieceMovementStrategy pieceMovementStrategy;

    private final GridPane grid = new GridPane();
    private final Set<PieceRectangle> pieces = new HashSet<>();
    private final Set<Tile> tiles = new HashSet<>();
    private final int rows;
    private final int columns;
    private DoubleBinding tileDimension;

    public GraphicalBoard(final int rows, final int columns) {
        this(rows, columns, new NonMovableGraphicPieceMovementStrategy());
    }

    public GraphicalBoard(final int rows, final int columns,
            final GraphicPieceMovementStrategy graphicalPieceMovementStrategy) {
        this(rows, columns, graphicalPieceMovementStrategy, new NonNavigableHistoryKeyHandlerStrategy());
    }

    public GraphicalBoard(final int rows, final int columns, final GraphicPieceMovementStrategy pieceMovementStrategy,
            final HistoryKeyHandlerStrategy historyKeyHandlerStrategy) {
        this.rows = rows;
        this.columns = columns;
        this.setGraphicPieceMovementStrategy(pieceMovementStrategy);
        this.setHistoryKeyHandlerStrategy(historyKeyHandlerStrategy);
        this.tileDimension = this.widthProperty().divide(Math.max(rows, columns));
        this.getChildren().add(this.grid);
    }

    public final void setGraphicPieceMovementStrategy(final GraphicPieceMovementStrategy pieceMovementStrategy) {
        this.pieceMovementStrategy = pieceMovementStrategy;
    }

    public final void setHistoryKeyHandlerStrategy(final HistoryKeyHandlerStrategy historyKeyHandlerStrategy) {
        this.getGrid().setOnKeyPressed(historyKeyHandlerStrategy);
    }

    public final GridPane getGrid() {
        return this.grid;
    }

    public final Set<PieceRectangle> getPieces() {
        return this.pieces;
    }

    protected final Set<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * 
     */
    protected void createBoard() {
        IntStream.range(0, this.rows).forEach(row -> {
            IntStream.range(0, this.columns).forEach(col -> {
                final Tile tile = new Tile(this.getGridPositionFromBoardPosition(new BoardPositionImpl(col, row)),
                        this.tileDimension, this.rows);
                this.tiles.add(tile);
                this.grid.add(tile, col, row);
            });
        });
    }

    /**
     * 
     * @param position
     * @return the board coordinate
     */
    protected BoardPosition getGridPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(), this.rows - 1 - position.getY());
    }

    private void addPieceToBoard(final PieceRectangle piece) {
        final BoardPosition realPosition = this.getGridPositionFromBoardPosition(piece.getPiece().getPiecePosition());
        this.grid.add(piece, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(piece, HPos.CENTER);
    }

    /**
     * 
     * @param piece
     */
    protected void drawPiece(final Piece piece) {
        final PieceRectangle pieceViewPort = new PieceRectangle(piece, this.imageLoader.getPieceImage(piece),
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
        board.getPieces().forEach(this::drawPiece);
    }
}
