package jhaturanga.views.match;

import javafx.scene.layout.Pane;
import jhaturanga.model.board.BoardPosition;

public class Tile extends Pane {
    private final BoardPosition boardPosition;

    public Tile(final BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

    public final BoardPosition getBoardPosition() {
        return this.boardPosition;
    }

}
