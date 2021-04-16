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

/**
 * Tile for the background of the board.
 */
public class Tile extends Pane {

    private static final double MARGIN_LEFT = 2.0;
    private static final double MARGIN_TOP = 2.0;
    private static final double MARGIN_RIGHT = 3.0;
    private static final double MARGIN_BOTTOM = 1.0;

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
            label.layoutXProperty().bind(this.widthProperty().subtract(label.widthProperty()).subtract(MARGIN_RIGHT));
            label.layoutYProperty()
                    .bind(this.heightProperty().subtract(label.heightProperty()).subtract(MARGIN_BOTTOM));
            label.setTextFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }
        if (col == 0) {
            final Label label = new Label(String.valueOf(this.rows - row));
            this.getChildren().add(label);
            label.layoutXProperty().set(MARGIN_LEFT);
            label.layoutYProperty().set(MARGIN_TOP);
            label.setTextFill((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }
    }

    /**
     * Highlight the position for possible destionation showing.
     * 
     * @param isPiecePresent - true if a piece is present, false otherwise.
     */
    public final void highlightPosition(final boolean isPiecePresent) {
        this.circle = new CircleHighlight(this, isPiecePresent);
        this.getChildren().add(this.circle);
    }

    /**
     * Reset the highlight.
     */
    public final void resetHighlightPosition() {
        Optional.ofNullable(this.circle).ifPresent(this.getChildren()::remove);
        this.circle = null;
    }

    /**
     * @return the board position.
     */
    public final BoardPosition getBoardPosition() {
        return this.boardPosition;
    }

    /**
     * Highlight as a part of a movement.
     */
    public final void highlightMovement() {
        this.setStyle(PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR);
    }

    /**
     * Reset the highlighted movement.
     */
    public final void resetHighlightMovement() {
        this.setStyle(this.baseColorStyle);
    }

}
