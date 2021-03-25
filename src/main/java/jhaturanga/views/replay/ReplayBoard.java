package jhaturanga.views.replay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.prism.paint.Color;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jhaturanga.commons.Pair;
import jhaturanga.commons.graphics.TileImpl;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.commons.style.PieceStyle;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public final class ReplayBoard extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final ReplayController replayController;
    private final GridPane grid = new GridPane();
    private final Map<Rectangle, Piece> pieces = new HashMap<>();
    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage;
    private final Board firstBoard;

    public ReplayBoard(final ReplayController replayController) {
        this.replayController = replayController;
        this.piecesImage = new HashMap<>();

        this.loadImages();
        this.setupHistoryKeysHandler();

        this.getChildren().add(this.grid);
        this.firstBoard = this.replayController.getFirstBoard();
        this.drawBoard(this.firstBoard);
        this.redraw(this.firstBoard);
        Platform.runLater(() -> this.grid.requestFocus());
    }

    /*
     * Setup the handler for history navigation
     */
    private void setupHistoryKeysHandler() {
        this.grid.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.A)) {
                this.replayController.getPrevBoard().ifPresent(board -> {
                    this.redraw(board);
                    Sound.play(SoundsEnum.MOVE);
                });

            } else if (e.getCode().equals(KeyCode.D)) {
                this.replayController.getNextBoard().ifPresent(board -> {
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
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                final TileImpl tile = new TileImpl(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));
                this.grid.add(tile, j, i);
            }
        }
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(), this.firstBoard.getRows() - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();

        pieceViewPort.setFill(
                new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer().getColor()))));

        pieceViewPort.widthProperty()
                .bind(this.grid.widthProperty().divide(this.firstBoard.getColumns()).divide(PIECE_SCALE));
        pieceViewPort.heightProperty()
                .bind(this.grid.heightProperty().divide(this.firstBoard.getRows()).divide(PIECE_SCALE));

        this.pieces.put(pieceViewPort, piece);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());
        this.grid.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void loadImages() {

        Arrays.stream(PieceType.values()).forEach(pieceType -> {
            Image img = new Image(PieceStyle.getPieceStylePath(pieceType, PlayerColor.WHITE));
            this.piecesImage.put(new Pair<>(pieceType, PlayerColor.WHITE), img);
            img = new Image(PieceStyle.getPieceStylePath(pieceType, PlayerColor.BLACK));
            this.piecesImage.put(new Pair<>(pieceType, PlayerColor.BLACK), img);
        });

    }

    private void redraw(final Board board) {
        this.grid.getChildren().removeAll(this.pieces.keySet());
        board.getPiecesStatus().forEach(i -> this.drawPiece(i));
    }

}
