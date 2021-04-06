package jhaturanga.commons.settings.media.style.piece;

import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public final class PieceStyle {

    private static PieceStyleEnum currentStyle = PieceStyleEnum.CLASSIC;

    private PieceStyle() {
    }

    /**
     * set piece style.
     * 
     * @param style piece witch want set
     */
    public static void setPieceStyle(final PieceStyleEnum style) {
        currentStyle = style;
    }

    /**
     * get previously set or default piece style.
     * 
     * @return piece style
     */
    public static PieceStyleEnum getPieceStyle() {
        return currentStyle;
    }

    /**
     * get path witch previously set or default piece style.
     * 
     * @return path of current piece style
     */
    public static String getPieceStylePath() {
        return currentStyle.getPath();
    }

    /**
     * Get path of selected piece using using the previously set or default piece
     * style.
     * 
     * @param piece
     * @param pieceColor
     * @return path of the piece.
     */
    public static String getPieceStylePath(final PieceType piece, final PlayerColor pieceColor) {

        return getPieceStylePath(currentStyle, piece, pieceColor);
    }

    /**
     * Get path of selected piece using using style assigned.
     * 
     * @param style
     * @param piece
     * @param pieceColor
     * @return path of selected piece.
     */
    public static String getPieceStylePath(final PieceStyleEnum style, final PieceType piece,
            final PlayerColor pieceColor) {

        return ClassLoader
                .getSystemResource(style.getPath() + pieceColor.toString().charAt(0) + "_" + piece.toString() + ".png")
                .toString();
    }

}
