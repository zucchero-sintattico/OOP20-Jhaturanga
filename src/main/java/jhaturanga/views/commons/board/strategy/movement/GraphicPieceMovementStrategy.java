package jhaturanga.views.commons.board.strategy.movement;

import javafx.scene.input.MouseEvent;

/**
 * The strategy of piece movement on the graphic board.
 */
public interface GraphicPieceMovementStrategy {

    /**
     * Handle when the user click on the piece.
     * 
     * @param event
     */
    void onPiecePressed(MouseEvent event);

    /**
     * Handle when the user drag a piece.
     * 
     * @param event
     */
    void onPieceDragged(MouseEvent event);

    /**
     * Handle when the user release a dragged piece.
     * 
     * @param event
     */
    void onPieceReleased(MouseEvent event);
}
