package jhaturanga.views.editor;

import javafx.scene.shape.Rectangle;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class PieceRectangleImpl extends Rectangle implements PieceRectangle {

    private final Piece piece;

    public PieceRectangleImpl(final Piece piece) {
        this.piece = piece;
    }

    @Override
    public final Piece getPiece() {
        return this.piece;
    }

    @Override
    public final PlayerColor getPieceColor() {
        return this.piece.getPlayer().getColor();
    }

    @Override
    public final PieceType getPieceType() {
        return this.piece.getType();
    }

}
