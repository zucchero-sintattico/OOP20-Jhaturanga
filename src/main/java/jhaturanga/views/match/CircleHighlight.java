package jhaturanga.views.match;

import javafx.scene.paint.Color;

public interface CircleHighlight {
    /**
     * Use to get the Circle's color.
     * 
     * @return Color is the Circle's actual color.
     */
    Color getCircleColor();

    /**
     * This method is called from the container when it's hovered.
     */
    void onMouseEntered();

    /**
     * This method is called from the container when it's not hovered anymore.
     */
    void onMouseExited();
}
