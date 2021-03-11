package jhaturanga.views.match;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleHighlightImpl extends Circle implements CircleHighlight {

    private static final int SMALL_SIZE_RATIO = 6;
    private static final int BIG_SIZE_RATIO = 2;
    private static final int STROKE_SIZE = 2;
    private static final int STROKE_HOVERED_SIZE = 4;
    private static final int RGB_VALUE = 200;
    private static final double ALPHA = 0.5f;
    private Color circleColor;

    public CircleHighlightImpl(final TileImpl tile, final boolean isPiecePresent) {
        if (isPiecePresent) {
            this.setRadius(tile.getWidth() / BIG_SIZE_RATIO);
            this.circleColor = Color.YELLOW;
            this.setStroke(this.circleColor);
            this.setStrokeWidth(STROKE_SIZE);
        } else {
            this.setRadius(tile.getWidth() / SMALL_SIZE_RATIO);
            this.circleColor = Color.BLACK;
            this.setStroke(this.circleColor);
        }
        this.setFill(Color.rgb(RGB_VALUE, RGB_VALUE, RGB_VALUE, ALPHA));
        this.setCenterX(tile.getWidth() / 2);
        this.setCenterY(tile.getHeight() / 2);
        this.toFront();
    }

    @Override
    public final Color getCircleColor() {
        return circleColor;
    }

    @Override
    public final void onMouseEntered() {
        this.setStrokeWidth(STROKE_HOVERED_SIZE);
        this.setStroke(Color.GREEN);

    }

    @Override
    public final void onMouseExited() {
        this.setStrokeWidth(STROKE_SIZE);
        this.setStroke(this.circleColor);
    }

}
