package jhaturanga.views.commons.board;

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
import jhaturanga.views.commons.board.strategy.history.HistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.history.NonNavigableHistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.movement.GraphicPieceMovementStrategy;
import jhaturanga.views.commons.board.strategy.movement.NonMovableGraphicPieceMovementStrategy;
import jhaturanga.views.commons.component.PieceRectangle;
import jhaturanga.views.commons.component.Tile;
import jhaturanga.views.commons.image.PieceImageLoader;

/**
 * The entity that represent the graphical board.
 */
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

    /**
     * Set the GraphicPieceMovementStrategy.
     * 
     * @param pieceMovementStrategy - the strategy
     */
    public final void setGraphicPieceMovementStrategy(final GraphicPieceMovementStrategy pieceMovementStrategy) {
        this.pieceMovementStrategy = pieceMovementStrategy;
    }

    /**
     * Set the HistoryKeyHandlerStrategy.
     * 
     * @param historyKeyHandlerStrategy - the strategy
     */
    public final void setHistoryKeyHandlerStrategy(final HistoryKeyHandlerStrategy historyKeyHandlerStrategy) {
        this.getGrid().setOnKeyPressed(historyKeyHandlerStrategy);
    }

    /**
     * @return the grid.
     */
    public final GridPane getGrid() {
        return this.grid;
    }

    /**
     * @return all the peices in the board.
     */
    protected final Set<PieceRectangle> getPieces() {
        return this.pieces;
    }

    /**
     * @return the tiles
     */
    protected final Set<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * Create the graphical board.
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
     * Convert board position to the effective grid position.
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

    private void drawPiece(final Piece piece) {
        final PieceRectangle pieceViewPort = new PieceRectangle(piece, this.imageLoader.getPieceImage(piece),
                this.tileDimension.divide(PIECE_SCALE), this.pieceMovementStrategy);
        this.pieces.add(pieceViewPort);
        this.addPieceToBoard(pieceViewPort);
    }

    /**
     * Redraw a new board.
     * 
     * @param board - the board to be redraw
     */
    public void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces);
        board.getPieces().forEach(this::drawPiece);
    }
}
