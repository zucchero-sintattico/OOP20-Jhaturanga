package jhaturanga.views.board;

import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public final class BoardView extends Pane {

    private static final double PIECE_SCALE = 1.5;
    private final int rows;
    private final int columns;
    // private final Map<Pair<Integer, Integer>, Pane> board;

    private final GridPane grid;

    private int oldCol;
    private int oldRow;

    public BoardView(final ObservableDoubleValue width, final ObservableDoubleValue height, final int rows,
            final int columns) {
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
        this.rows = rows;
        this.columns = columns;
        // this.board = new HashMap<>();

        this.grid = new GridPane();
        this.getChildren().add(this.grid);
        this.grid.setGridLinesVisible(true);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                final Pane tile = new Pane();

                tile.setStyle((i + j) % 2 == 0 ? "-fx-background-color:#CCC" : "-fx-background-color:#333");

                final var pos = new Pair<>(i, j);
                tile.setOnMouseClicked(e -> {
                    System.out.println(pos);
                });

                tile.prefWidthProperty().bind(this.widthProperty().divide(columns));
                tile.prefHeightProperty().bind(this.heightProperty().divide(rows));

                // this.board.put(pos, tile);
                this.grid.add(tile, j, i);
            }
        }
    }

    public void movePiece(final Rectangle r, final int row, final int column) {

        // TODO
        // Image img = new Image("file:" +
        // getClass().getResource("king.png").getFile());
        // r.setFill(new ImagePattern(img));
        r.setFill(Paint.valueOf("#00ff11"));

        r.widthProperty().bind(this.grid.widthProperty().divide(columns).divide(PIECE_SCALE));
        r.heightProperty().bind(this.grid.heightProperty().divide(rows).divide(PIECE_SCALE));

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

            final int newCol = (int) (((e.getSceneX() - this.getLayoutX()) / this.getWidth()) * columns);
            final int newRow = (int) (((e.getSceneY() - this.getLayoutY()) / this.getHeight()) * rows);

            System.out.println(newCol + " " + newRow);
            System.out.println(e.getSceneY() - this.getLayoutY());

            if (newCol >= columns || newRow >= rows || newRow < 0 || newCol < 0
                    || (e.getSceneX() - this.getLayoutX()) < 0 || (e.getSceneY() - this.getLayoutY()) < 0) {

                System.out.println("Impossibile");
                this.getChildren().remove(r);
                this.grid.add(r, oldCol, oldRow);

            } else {
                this.getChildren().remove(r);
                this.grid.add(r, newCol, newRow);
            }

        });

        this.grid.add(r, column, row);
        GridPane.setHalignment(r, HPos.CENTER);
    }

    public int getRowsNumber() {
        return this.rows;
    }

    public int getColumnsNumber() {
        return this.columns;
    }

}
