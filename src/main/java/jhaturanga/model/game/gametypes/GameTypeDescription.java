package jhaturanga.model.game.gametypes;

public final class GameTypeDescription {

    private GameTypeDescription() {
    }

    public static String classicGameType() {
        return "This is the classic game of chess";
    }

    public static String pawnMovemementVariant() {
        return "In this variant pawns can move in all direction by one step, except "
                + "for directions opposite to their normal one.";
    }

    public static String pieceSwapVariant() {
        return "In this variant, when pieces such as ROOKs, BISHOPs or KNIGHTs are moved, they "
                + "transform in a different piece following this exact path: "
                + "ROOK -> BISHOP \nBISHOP -> KNIGHT \nKNIGHT -> ROOK\n";
    }

    public static String pawnHordeVariant() {
        return "In this variant, white players starts with 17 pawns and a King to defend.\nThat's it.";
    }

    public static String threeColumnsVariant() {
        return "In this variant, the board only has 3 columns.";
    }

}
