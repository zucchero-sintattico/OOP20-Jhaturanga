package jhaturanga.views.commons.component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import jhaturanga.model.board.BoardPosition;

public class Tile extends Pane {

    private static final List<String> LETTERS = Arrays
            .asList("a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" "));

    private int rows;

    private final BoardPosition boardPosition;
    private String baseColorStyle;
    private static final String PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR = "-fx-background-color:#FFE57C;";
    private CircleHighlight circle;

    public Tile(final BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

    public Tile(final BoardPosition boardPosition, final DoubleBinding dimension, final int rows) {
        this.rows = rows;
        this.boardPosition = boardPosition;

        this.prefWidthProperty().bind(dimension);
        this.prefHeightProperty().bind(dimension);
        this.checkIfNeedToDisplayCoordinate(this.boardPosition);

        this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                ? "-fx-background-color:#333;"
                : "-fx-background-color:#CCC;";
        this.setStyle(this.baseColorStyle);
        this.setPadding(new Insets(0));
    }

    private void checkIfNeedToDisplayCoordinate(final BoardPosition position) {
        final int col = position.getX();
        final int row = position.getY();

        if (row == 0) {
            final Label label = new Label(LETTERS.get(col));
            this.getChildren().add(label);
            final double marginRight = 3.0;
            final double marginBottom = 1.0;
            label.layoutXProperty().bind(this.widthProperty().subtract(label.widthProperty()).subtract(marginRight));
            label.layoutYProperty().bind(this.heightProperty().subtract(label.heightProperty()).subtract(marginBottom));
            label.setTextFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }
        if (col == 0) {
            final Label label = new Label(String.valueOf(this.rows - row));
            this.getChildren().add(label);
            final double marginTop = 2.0;
            final double marginLeft = 2.0;
            label.layoutXProperty().set(marginLeft);
            label.layoutYProperty().set(marginTop);
            label.setTextFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }
    }

    public final void highlightPosition(final boolean isPiecePresent) {
        this.circle = new CircleHighlight(this, isPiecePresent);
        this.getChildren().add(this.circle);
    }

    public final void resetHighlightPosition() {
        Optional.ofNullable(this.circle).ifPresent(this.getChildren()::remove);
        this.circle = null;
    }

    public final BoardPosition getBoardPosition() {
        return this.boardPosition;
    }

    public final void highlightMovement() {
        this.setStyle(PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR);
    }

    public final void resetHighlightMovement() {
        this.setStyle(this.baseColorStyle);
    }

}
