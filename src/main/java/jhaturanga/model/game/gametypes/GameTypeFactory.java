package jhaturanga.model.game.gametypes;

import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.player.Player;
import jhaturange.model.chessproblems.ChessProblem;

public interface GameTypeFactory {
    /**
     * Use it to get a classicGameType.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a classicGameType.
     */
    GameType classicGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a pawnHordeVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a pawnHordeVariantGame.
     */
    GameType pawnHordeVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a pieceSwapVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a pieceSwapVariantGame.
     */
    GameType pieceSwapVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a pawnMovemementVariantGame.
     */
    GameType pawnMovemementVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a threeColumnsVariantGame.
     */
    GameType threeColumnsVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a oneDimensionVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a oneDimensionVariantGame.
     */
    GameType oneDimensionVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a rookBishopMovementVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a rookBishopMovementVariantGame.
     */
    GameType rookBishopMovementVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a bombVariantGame.
     */
    GameType bombVariantGame(Player whitePlayer, Player blackPlayer);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @param chessProblem - the problem chosen to be played.
     * @return GameType representing a bombVariantGame.
     */
    GameType chessProblemGameType(Player whitePlayer, Player blackPlayer, ChessProblem chessProblem);

    /**
     * Use it to get a customizedBoardVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @param startingBoardInfo - represents the board to build with the
     *                          BoardBuilderFactory. The board is a StringBoard.
     * @return GameType representing a customizedBoardVariantGame.
     */
    GameType customizedBoardVariantGame(Player whitePlayer, Player blackPlayer, StringBoard startingBoardInfo);

    /**
     * Use it to get a everyPieceMovesLikeRooksVariantGame.
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return GameType representing a everyPieceMovesLikeRooksVariantGame.
     */
    GameType everyPieceMovesLikeRooksVariantGame(Player whitePlayer, Player blackPlayer);

}
