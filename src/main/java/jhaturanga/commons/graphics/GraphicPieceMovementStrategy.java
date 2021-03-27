package jhaturanga.commons.graphics;

import javafx.scene.input.MouseEvent;

public interface GraphicPieceMovementStrategy {

    void onPieceClicked(MouseEvent event);

    void onPieceDragged(MouseEvent event);

    void onPieceReleased(MouseEvent event);
}
