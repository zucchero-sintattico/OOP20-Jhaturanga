package jhaturanga.views.commons.board.strategy.movement;

import javafx.scene.input.MouseEvent;

public interface GraphicPieceMovementStrategy {

    void onPiecePressed(MouseEvent event);

    void onPieceDragged(MouseEvent event);

    void onPieceReleased(MouseEvent event);
}
