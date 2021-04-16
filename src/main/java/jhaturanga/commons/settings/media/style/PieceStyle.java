package jhaturanga.commons.settings.media.style;

import java.nio.file.Path;

import jhaturanga.commons.settings.config.PieceStyleConfigStrategy;
import jhaturanga.commons.settings.filegetter.PieceStyleListStrategy;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public final class PieceStyle {
    private static PieceStyleListStrategy pieceStyleList = new PieceStyleListStrategy();
    private static PieceStyleConfigStrategy currentStyle = pieceStyleList.getAll().stream()
            .filter(e -> "shadow".contentEquals(e.getName())).findAny().get();

    private PieceStyle() {
    }

    /**
     * set piece style.
     * 
     * @param style piece witch want set
     */
    public static void setPieceStyle(final PieceStyleConfigStrategy style) {
        currentStyle = style;
    }

    /**
     * get previously set or default piece style.
     * 
     * @return piece style
     */
    public static PieceStyleConfigStrategy getPieceStyle() {
        return currentStyle;
    }

    /**
     * get path witch previously set or default piece style.
     * 
     * @return path of current piece style
     */
    public static Path getPieceStylePath() {
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
    public static Path getCurrentPieceStylePath(final PieceType piece, final PlayerColor pieceColor) {

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
    public static Path getPieceStylePath(final PieceStyleConfigStrategy style, final PieceType piece,
            final PlayerColor pieceColor) {
        return Path.of(style.getFilePath() + "/" + pieceColor.toString().charAt(0) + "_" + piece.toString() + ".png");
    }

}
