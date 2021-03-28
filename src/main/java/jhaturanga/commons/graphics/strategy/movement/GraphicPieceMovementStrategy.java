package jhaturanga.commons.graphics.strategy.movement;

import javafx.scene.input.MouseEvent;

public interface GraphicPieceMovementStrategy {

    void onPieceClicked(MouseEvent event);

    void onPieceDragged(MouseEvent event);

    void onPieceReleased(MouseEvent event);
}
