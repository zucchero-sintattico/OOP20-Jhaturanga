package jhaturanga.views.history;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import jhaturanga.commons.sound.Sound;
import jhaturanga.commons.sound.SoundsEnum;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.views.match.Tile;

public final class HistoryBoard extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final MatchController matchController;
    private final GridPane grid = new GridPane();
    private final Map<Rectangle, Piece> pieces = new HashMap<>();
    private final Map<Pair<PieceType, PlayerColor>, Image> piecesImage;

    public HistoryBoard(final MatchController matchController) {
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
            if (e.getCode().equals(KeyCode.A)) {
                final Optional<Board> board = this.matchController.getPrevBoard();
                System.out.println("STATE BOARD: " + board.get().getBoardState());
                if (board.isPresent()) {
                    this.redraw(board.get());
                    Sound.play(SoundsEnum.MOVE);
                }

            } else if (e.getCode().equals(KeyCode.D)) {
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
                final Tile tile = new Tile(this.getRealPositionFromBoardPosition(new BoardPositionImpl(j, i)));
                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));
                this.grid.add(tile, j, i);
            }
        }
    }

    private BoardPosition getRealPositionFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.matchController.getBoardStatus().getRows() - 1 - position.getY());
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();

        pieceViewPort.setFill(
                new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer().getColor()))));

        pieceViewPort.widthProperty().bind(this.grid.widthProperty()
                .divide(this.matchController.getBoardStatus().getColumns()).divide(PIECE_SCALE));
        pieceViewPort.heightProperty().bind(
                this.grid.heightProperty().divide(this.matchController.getBoardStatus().getRows()).divide(PIECE_SCALE));

        this.pieces.put(pieceViewPort, piece);

        final BoardPosition realPosition = this.getRealPositionFromBoardPosition(piece.getPiecePosition());
        this.grid.add(pieceViewPort, realPosition.getX(), realPosition.getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void loadImages() {
        List.of(this.matchController.getModel().getWhitePlayer(), this.matchController.getModel().getBlackPlayer())
                .stream().forEach(x -> {
                    Arrays.stream(PieceType.values()).forEach(i -> {
                        final Image img = new Image(
                                "file:" + ClassLoader
                                        .getSystemResource("piece/PNGs/No_shadow/1024h/"
                                                + x.getColor().toString().charAt(0) + "_" + i.toString() + ".png")
                                        .getFile());
                        this.piecesImage.put(new Pair<>(i, x.getColor()), img);
                    });
                });
    }

    public void redraw(final Board board) {
        final var toRemove = this.grid.getChildren().stream().filter(n -> n instanceof Rectangle)
                .collect(Collectors.toList());

        this.grid.getChildren().removeAll(toRemove);

        board.getBoardState().forEach(i -> this.drawPiece(i));
    }

}
