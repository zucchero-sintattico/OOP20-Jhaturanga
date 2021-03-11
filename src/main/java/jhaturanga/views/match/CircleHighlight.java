package jhaturanga.views.match;

import javafx.scene.paint.Color;

public interface CircleHighlight {
    /**
     * Use to get the Circle's color.
     * 
     * @return Color is the Circle's actual color.
     */
    Color getCircleColor();

    void onMouseEntered();

    void onMouseExited();
}
