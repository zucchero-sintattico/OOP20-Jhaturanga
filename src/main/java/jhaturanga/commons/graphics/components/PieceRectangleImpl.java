package jhaturanga.commons.graphics.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jhaturanga.commons.graphics.strategy.movement.GraphicPieceMovementStrategy;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class PieceRectangleImpl extends Rectangle implements PieceRectangle {

    private final Piece piece;

    public PieceRectangleImpl(final Piece piece) {
        this.piece = piece;
    }

    public PieceRectangleImpl(final Piece piece, final Image image, final DoubleBinding widthProperty,
            final GraphicPieceMovementStrategy movementStrategy) {
        this.piece = piece;
        this.setFill(new ImagePattern(image));
        this.widthProperty().bind(widthProperty);
        this.heightProperty().bind(widthProperty);

        this.setOnMousePressed(movementStrategy::onPieceClicked);
        this.setOnMouseDragged(movementStrategy::onPieceDragged);
        this.setOnMouseReleased(movementStrategy::onPieceReleased);
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
