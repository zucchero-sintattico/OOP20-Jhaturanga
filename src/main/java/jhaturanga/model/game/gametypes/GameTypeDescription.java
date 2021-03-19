package jhaturanga.model.game.gametypes;

public final class GameTypeDescription {

    private GameTypeDescription() {
    }

    public static String classicGameType() {
        return "This is the classic game of chess";
    }

    public static String pawnMovemementVariant() {
        return "In this variant Pawns can move in all direction by one step, except "
                + "for directions opposite to their normal one.\n"
                + "Also, Pawns can eat in every direction they can move."
                + "Pawns can no longer move by two steps at their first movement.";
    }

    public static String pieceSwapVariant() {
        return "In this variant, when pieces such as ROOKs, BISHOPs or KNIGHTs are moved, they \n"
                + "transform in a different piece following this exact pattern: \n"
                + "ROOK -> BISHOP \nBISHOP -> KNIGHT \nKNIGHT -> ROOK\n" + "\nNote: Castling is no longer possible.";
    }

    public static String pawnHordeVariant() {
        return "In this variant, White Player starts with 16 Pawns and" + "\na King front-row to defend"
                + "\n\nThat's it.";
    }

    public static String threeColumnsVariant() {
        return "In this variant, the board only has 3 columns.\n" + "Simple as that.";
    }

    public static String oneDimensionVariant() {
        return "In this variant, the board can be imagined as a one-dimension plane.\nKnights move by two steps back and forth.";
    }

    public static String customizedBoard() {
        return "In this variant, the board is created in the BoardEditor.\n"
                + "Because of this Castling is obviously disabled.";
    }

}
