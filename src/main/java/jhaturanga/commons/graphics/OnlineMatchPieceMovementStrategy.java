package jhaturanga.commons.graphics;

import javafx.scene.input.MouseEvent;
import jhaturanga.views.editor.PieceRectangleImpl;

public final class OnlineMatchPieceMovementStrategy extends NormalMatchPieceMovementStrategy {

    private final boolean isWhite;

    public OnlineMatchPieceMovementStrategy(final MatchBoard board, final boolean isWhite) {
        super(board);
        this.isWhite = isWhite;
    }

    @Override
    public void onPieceClicked(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        super.onPieceClicked(event);
    }

}
