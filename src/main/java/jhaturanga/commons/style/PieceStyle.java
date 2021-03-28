package jhaturanga.commons.style;

import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public final class PieceStyle {

    private static PieceStyleEnum currentStyle = PieceStyleEnum.CLASSIC;

    // TODO : tommaso commenta per bene

    private PieceStyle() {
    }

    /**
     * 
     * @param style piece witch want set
     */
    public static void setPieceStyle(final PieceStyleEnum style) {
        currentStyle = style;
    }

    /**
     * 
     * @return current piece style
     */
    public static PieceStyleEnum getPieceStyle() {
        return currentStyle;
    }

    /**
     * 
     * @return path of current application piece style
     */
    public static String getPieceStylePath() {
        return currentStyle.getPath();
    }

    /**
     * 
     * @param piece
     * @param pieceColor
     * @return path of the piece.
     */
    public static String getPieceStylePath(final PieceType piece, final PlayerColor pieceColor) {

        return getPieceStylePath(currentStyle, piece, pieceColor);
    }

    /**
     * 
     * @param style
     * @param piece
     * @param pieceColor
     * @return path of selected piece.
     */
    public static String getPieceStylePath(final PieceStyleEnum style, final PieceType piece,
            final PlayerColor pieceColor) {

        return ClassLoader
                .getSystemResource(
                        style.getPath() + "/1024h/" + pieceColor.toString().charAt(0) + "_" + piece.toString() + ".png")
                .toString();
    }

}
