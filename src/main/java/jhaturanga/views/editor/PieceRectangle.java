package jhaturanga.views.editor;

import javafx.scene.shape.Rectangle;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class PieceRectangle extends Rectangle {

    private final Piece piece;

    public PieceRectangle(final Piece piece) {
        this.piece = piece;
    }

    public final Piece getPiece() {
        return this.piece;
    }

    public final PlayerColor getPieceColor() {
        return this.piece.getPlayer().getColor();
    }

    public final PieceType getPieceType() {
        return this.piece.getType();
    }

}
