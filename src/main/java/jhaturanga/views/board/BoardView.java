package jhaturanga.views.board;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final GameController gameController;
    private final GridPane grid;
    private final Map<Rectangle, Pair<Integer, Integer>> piecesPosition;

    //TODO: implement image caching for quickly redraw
    private final Map<Piece, Image> piecesImage;

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

        pieceViewPort.setFill(new ImagePattern(this.piecesImage.get(piece)));

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

                this.removePieceAtPosition(newCol, newRow);

                this.piecesPosition.remove(pieceViewPort);
                this.piecesPosition.put(pieceViewPort, new Pair<>(newCol, newRow));

                this.getChildren().remove(pieceViewPort);
                this.grid.add(pieceViewPort, newCol, newRow);
/*
                final Optional<Piece> pieceToCheckImgUpdate = this.gameController.getBoardStatus()
                        .getPieceAtPosition(new BoardPositionImpl(newCol, newRow));

                if (pieceToCheckImgUpdate.isPresent()) {
                    final Piece pieceToCheck = pieceToCheckImgUpdate.get();

                    final Optional<Rectangle> rect = this.grid.getChildren().stream()
                            .filter(x -> x instanceof Rectangle).map(x -> (Rectangle) x)
                            .filter(x -> GridPane.getColumnIndex(x).equals(pieceToCheck.getPiecePosition().getX())
                                    && GridPane.getRowIndex(x).equals(pieceToCheck.getPiecePosition().getY()))
                            .findAny();

                    final Image image = new Image("file:" + ClassLoader.getSystemResource(
                            "piece/PNGs/No_shadow/1024h/" + pieceToCheck.getPlayer().getColor().toString().charAt(0)
                                    + "_" + pieceToCheck.getType().toString() + ".png")
                            .getFile());

                    rect.get().setFill(new ImagePattern(image));
                }*/
            }

        });

        this.piecesPosition.put(pieceViewPort, new Pair<>(piece.getPiecePosition().getX(), piece.getPiecePosition().getY()));
        this.grid.add(pieceViewPort, piece.getPiecePosition().getX(), piece.getPiecePosition().getY());
        GridPane.setHalignment(pieceViewPort, HPos.CENTER);
    }

    private void removePieceAtPosition(final int column, final int row) {
        final Optional<Rectangle> pieceToRemove = this.piecesPosition.entrySet().stream()
                .filter(e -> e.getValue().getKey() == column)
                .filter(e -> e.getValue().getValue() == row)
                .map(e -> e.getKey())
                .findFirst();
        pieceToRemove.ifPresent(p -> {
            this.grid.getChildren().remove(p);
            this.piecesPosition.remove(p);
        });
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
            this.piecesImage.put(p, img);
        });
    }

    public void redraw() {
        this.gameController.getBoardStatus().getBoardState().forEach(i -> this.drawPiece(i));
    }

}
