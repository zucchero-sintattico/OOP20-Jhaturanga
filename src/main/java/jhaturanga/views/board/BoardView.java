package jhaturanga.views.board;

import java.util.Optional;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jhaturanga.controllers.game.GameController;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;

    private final GameController gameController;
    private final GridPane grid;

    private int oldCol;
    private int oldRow;

    public BoardView(final GameController gameController) {
        this.gameController = gameController;

        this.grid = new GridPane();
        this.getChildren().add(this.grid);
        this.grid.setGridLinesVisible(true);

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

    public void addPiece(final Piece piece) {

        // TODO: ajdust
        final Rectangle r = new Rectangle();
        final Image img = new Image("file:" + ClassLoader.getSystemResource("piece/PNGs/No_shadow/1024h/"
                + piece.getPlayer().getColor().toString().charAt(0) + "_" + piece.getType().toString() + ".png")
                .getFile());
        r.setFill(new ImagePattern(img));

        r.widthProperty().bind(this.grid.widthProperty().divide(this.gameController.getBoardStatus().getColumns())
                .divide(PIECE_SCALE));
        r.heightProperty().bind(
                this.grid.heightProperty().divide(this.gameController.getBoardStatus().getRows()).divide(PIECE_SCALE));

        r.setOnMousePressed(e -> {
            oldCol = GridPane.getColumnIndex(r);
            oldRow = GridPane.getRowIndex(r);
            this.grid.getChildren().remove(r);
            this.getChildren().add(r);
        });

        r.setOnMouseDragged(e -> {

            r.setX(e.getX() - r.getWidth() / 2);
            r.setY(e.getY() - r.getHeight() / 2);
        });

        r.setOnMouseReleased(e -> {

            System.out.println(this.grid.getWidth());
            final int newCol = (int) (((e.getSceneX() - this.getLayoutX()) / this.grid.getWidth())
                    * this.gameController.getBoardStatus().getColumns());
            final int newRow = (int) (((e.getSceneY() - this.getLayoutY()) / this.grid.getHeight())
                    * this.gameController.getBoardStatus().getRows());

            System.out.println(newCol + " " + newRow);

            if (newCol >= this.gameController.getBoardStatus().getColumns()
                    || newRow >= this.gameController.getBoardStatus().getRows() || newRow < 0 || newCol < 0
                    || (e.getSceneX() - this.getLayoutX()) < 0 || (e.getSceneY() - this.getLayoutY()) < 0
                    || !this.gameController.move(new BoardPositionImpl(oldCol, oldRow),
                            new BoardPositionImpl(newCol, newRow))) {

                System.out.println("Impossibile");
                this.getChildren().remove(r);
                this.grid.add(r, oldCol, oldRow);

            } else {

                final Optional<Node> pieceToRemoveAtNewPos = this.grid.getChildren().stream()
                        .filter(i -> i instanceof Rectangle).filter(i -> GridPane.getColumnIndex(i).equals(newCol)
                                && GridPane.getRowIndex(i).equals(newRow))
                        .findAny();
                if (pieceToRemoveAtNewPos.isPresent()) {
                    this.grid.getChildren().remove(pieceToRemoveAtNewPos.get());
                }
                this.getChildren().remove(r);
                this.grid.add(r, newCol, newRow);
            }

            // this.gameController.getBoardStatus().getBoardState().forEach(i ->
            // addPiece(i));

        });

        this.grid.add(r, piece.getPiecePosition().getX(), piece.getPiecePosition().getY());
        GridPane.setHalignment(r, HPos.CENTER);
    }

    public int getRowsNumber() {
        return this.gameController.getBoardStatus().getRows();
    }

    public int getColumnsNumber() {
        return this.gameController.getBoardStatus().getColumns();
    }

}
