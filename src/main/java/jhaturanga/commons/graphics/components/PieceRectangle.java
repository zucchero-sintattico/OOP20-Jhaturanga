package jhaturanga.commons.graphics.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import jhaturanga.commons.graphics.strategy.movement.GraphicPieceMovementStrategy;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class PieceRectangle extends Rectangle {

    private final Piece piece;

    public PieceRectangle(final Piece piece) {
        this.piece = piece;
    }

    public PieceRectangle(final Piece piece, final Image image, final DoubleBinding widthProperty,
            final GraphicPieceMovementStrategy movementStrategy) {
        this.piece = piece;
        this.setFill(new ImagePattern(image));
        this.widthProperty().bind(widthProperty);
        this.heightProperty().bind(widthProperty);
        this.setOnMousePressed(movementStrategy::onPiecePressed);
        this.setOnMouseDragged(movementStrategy::onPieceDragged);
        this.setOnMouseReleased(movementStrategy::onPieceReleased);
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
