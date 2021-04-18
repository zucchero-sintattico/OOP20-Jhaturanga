package jhaturanga.views.commons.board.strategy.movement;

import javafx.scene.input.MouseEvent;

/**
 * A non-movable piece movement strategy.
 */
public final class NonMovableGraphicPieceMovementStrategy implements GraphicPieceMovementStrategy {
    /**
     * {@inheritDoc}
     */
    @Override
    public void onPiecePressed(final MouseEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPieceDragged(final MouseEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPieceReleased(final MouseEvent event) {
    }

}
