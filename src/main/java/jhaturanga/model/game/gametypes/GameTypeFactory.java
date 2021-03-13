package jhaturanga.model.game.gametypes;

import jhaturanga.model.player.Player;

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

}
