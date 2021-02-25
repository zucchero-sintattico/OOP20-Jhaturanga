package jhaturanga.views.board;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import jhaturanga.controllers.game.GameController;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final GameController gameController;
    private final GridPane grid;
    private final Map<Rectangle, Pair<Integer, Integer>> piecesPosition;

    //TODO: implement image caching for quickly redraw
    private final Map<Pair<PieceType, Player>, Image> piecesImage;

    public BoardView(final GameController gameController) {
        this.gameController = gameController;
        this.piecesPosition = new HashMap<>();

        this.piecesImage = new HashMap<>();
        this.loadImages();

        this.grid = new GridPane();
        this.getChildren().add(this.grid);

        this.drawBoard();
        this.redraw();
    }

    private void drawBoard() {
        final int bigger = Integer.max(this.gameController.getBoardStatus().getColumns(),
                this.gameController.getBoardStatus().getRows());

        for (int i = 0; i < this.gameController.getBoardStatus().getRows(); i++) {
            for (int j = 0; j < this.gameController.getBoardStatus().getColumns(); j++) {
                final Pane tile = new Pane();

                // TODO: adjust for style.
                tile.setStyle((i + j) % 2 == 0 ? "-fx-background-color:#CCC" : "-fx-background-color:#333");

                tile.prefWidthProperty().bind(this.widthProperty().divide(bigger));
                tile.prefHeightProperty().bind(this.heightProperty().divide(bigger));

                this.grid.add(tile, j, i);
            }
        }
    }

    private void drawPiece(final Piece piece) {

        final Rectangle pieceViewPort = new Rectangle();

        pieceViewPort.setFill(new ImagePattern(this.piecesImage.get(new Pair<>(piece.getType(), piece.getPlayer()))));

        pieceViewPort.widthProperty()
            .bind(this.grid.widthProperty()
                    .divide(this.gameController.getBoardStatus().getColumns())
                    .divide(PIECE_SCALE));
        pieceViewPort.heightProperty()
            .bind(this.grid.heightProperty()
                    .divide(this.gameController.getBoardStatus().getRows())
                    .divide(PIECE_SCALE));

        pieceViewPort.setOnMousePressed(e -> {
            this.grid.getChildren().remove(pieceViewPort);
            this.getChildren().add(pieceViewPort);
        });

        pieceViewPort.setOnMouseDragged(e -> {
            pieceViewPort.setX(e.getX() - pieceViewPort.getWidth() / 2);
            pieceViewPort.setY(e.getY() - pieceViewPort.getHeight() / 2);
        });

        pieceViewPort.setOnMouseReleased(e -> {

            final int oldCol = this.piecesPosition.get(pieceViewPort).getKey();
            final int oldRow = this.piecesPosition.get(pieceViewPort).getValue();

            final int newCol = (int) (((e.getSceneX() - this.getLayoutX()) / this.grid.getWidth())
                    * this.gameController.getBoardStatus().getColumns());
            final int newRow = (int) (((e.getSceneY() - this.getLayoutY()) / this.grid.getHeight())
                    * this.gameController.getBoardStatus().getRows());


            if ((e.getSceneX() - this.getLayoutX()) < 0
                || (e.getSceneY() - this.getLayoutY()) < 0) { //Out of Board
                System.out.println("Out of Board");
                this.abortMove(pieceViewPort);
            } else if (!this.gameController.move(
                    new BoardPositionImpl(oldCol, oldRow),
                    new BoardPositionImpl(newCol, newRow))) { //Illegal Move
                System.out.println("Illegal Move");
                this.abortMove(pieceViewPort);
            } else { //move

                this.getChildren().remove(pieceViewPort);
                this.grid.add(pieceViewPort, newCol, newRow);
                this.redraw();
            }

        });

        this.piecesPosition.put(pieceViewPort, new Pair<>(piece.getPiecePosition().getX(), piece.getPiecePosition().getY()));
        this.grid.add(pieceViewPort, piece.getPiecePosition().getX(), piece.getPiecePosition().getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void abortMove(final Rectangle piece) {
        this.getChildren().remove(piece);
        this.grid.add(piece, 
                this.piecesPosition.get(piece).getKey(), 
                this.piecesPosition.get(piece).getValue());
    }

    private void loadImages() {
        this.gameController.getBoardStatus().getBoardState().forEach(p -> {
            final Image img = new Image("file:" + ClassLoader.getSystemResource("piece/PNGs/No_shadow/1024h/"
                    + p.getPlayer().getColor().toString().charAt(0) + "_" + p.getType().toString() + ".png")
                    .getFile());
            this.piecesImage.put(new Pair<>(p.getType(), p.getPlayer()), img);
        });
    }

    public void redraw() {
        final var toRemove = this.grid.getChildren().stream()
                .filter(n -> n instanceof Rectangle)
                .collect(Collectors.toList());
        this.grid.getChildren().removeAll(toRemove);
        this.piecesPosition.clear();

        this.gameController.getBoardStatus().getBoardState().forEach(i -> this.drawPiece(i));
    }

}
