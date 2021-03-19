package jhaturanga.views.editor;

import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public interface PieceRectangle {

    /**
     * 
     * @return Piece - the piece in the Rectangle.
     */
    Piece getPiece();

    /**
     * 
     * @return PlayerColor - the color of the piece in the Rectangle.
     */
    PlayerColor getPieceColor();

    /**
     * 
     * @return PieceType - the PieceType of the Piece in the Rectangle.
     */
    PieceType getPieceType();
}
