package jhaturanga.views.match;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleHighlight extends Circle {

    private static final int SMALL_SIZE_RATIO = 6;
    private static final int BIG_SIZE_RATIO = 2;
    private static final int STROKE_SIZE = 2;
    private static final int RGB_VALUE = 200;
    private static final double ALPHA = 0.5f;

    public CircleHighlight(final Tile tile, final boolean isPiecePresent) {
        if (isPiecePresent) {
            this.setRadius(tile.getWidth() / BIG_SIZE_RATIO);
            this.setStroke(Color.YELLOW);
            this.setStrokeWidth(STROKE_SIZE);
        } else {
            this.setRadius(tile.getWidth() / SMALL_SIZE_RATIO);
            this.setStroke(Color.BLACK);
        }

        this.setFill(Color.rgb(RGB_VALUE, RGB_VALUE, RGB_VALUE, ALPHA));
        this.setCenterX(tile.getWidth() / 2);
        this.setCenterY(tile.getHeight() / 2);
        this.toFront();
    }
}
