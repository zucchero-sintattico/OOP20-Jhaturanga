package jhaturanga.model.game.type;

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

    // TODO: NO MORE NEEDED
    public static String customizedBoard() {
        return "In this variant, the board is created in the BoardEditor.\n"
                + "Because of this Castling is obviously disabled.";
    }

    public static String bombVariant() {
        return "In this variant, every time a piece is captured, a bomb may explode, having as epicenter "
                + "the position of the last capture.\n"
                + "The range of the blast will destroy all pieces of whatever player, except Kings.\n"
                + "Range of the blast and timing are unknown.\n"
                + "Only the maximum blast's range is to be know: it's radious will never pass"
                + " half of the minimum between the number of rows and columns of the board";
    }

    public static String rookBishopMovementVariant() {
        return "In this variant, Rooks and Bishops both share the same MovementStrategy.\n"
                + "Their movement is a hybrid between their original movement from"
                + " the orthodox classic game of chess.\n" + "It's probably easier for you to try it.";
    }

    public static String everyoneMovesLikeRooks() {
        return "In this variant, every single piece has the same movementStrategy as classic Rooks.";
    }

    public static String kingMovesLikeQueen() {
        return "In this variant, the King moves like the classic Queen.";
    }

}
