package jhaturanga.model.game.factory;

import jhaturanga.model.chessproblems.ChessProblem;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.game.Game;
import jhaturanga.model.player.PlayerPair;

public interface GameFactory {

    /**
     * Use it to get a classicGameType.
     * 
     * @param players
     * @return GameType representing a classicGameType.
     */
    Game classicGame(PlayerPair players);

    /**
     * Use it to get a pawnHordeVariantGame.
     * 
     * @param players
     * @return GameType representing a pawnHordeVariantGame.
     */
    Game pawnHordeVariantGame(PlayerPair players);

    /**
     * Use it to get a pieceSwapVariantGame.
     * 
     * @param players
     * @return GameType representing a pieceSwapVariantGame.
     */
    Game pieceSwapVariantGame(PlayerPair players);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param players
     * @return GameType representing a pawnMovemementVariantGame.
     */
    Game pawnMovemementVariantGame(PlayerPair players);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param players
     * @return GameType representing a threeColumnsVariantGame.
     */
    Game threeColumnsVariantGame(PlayerPair players);

    /**
     * Use it to get a oneDimensionVariantGame.
     * 
     * @param players
     * @return GameType representing a oneDimensionVariantGame.
     */
    Game oneDimensionVariantGame(PlayerPair players);

    /**
     * Use it to get a rookBishopMovementVariantGame.
     * 
     * @param players
     * @return GameType representing a rookBishopMovementVariantGame.
     */
    Game rookBishopMovementVariantGame(PlayerPair players);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param players
     * @return GameType representing a bombVariantGame.
     */
    Game bombVariantGame(PlayerPair players);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param players
     * @param chessProblem - the problem chosen to be played.
     * @return GameType representing a bombVariantGame.
     */
    Game chessProblemGameType(PlayerPair players, ChessProblem chessProblem);

    /**
     * Use it to get a customizedBoardVariantGame.
     * 
     * @param players
     * @param startingBoardInfo - represents the board to build with the
     *                          BoardBuilderFactory. The board is a StringBoard.
     * @return GameType representing a customizedBoardVariantGame.
     */
    Game customizedBoardVariantGame(PlayerPair players, StringBoard startingBoardInfo);

    /**
     * Use it to get a everyPieceMovesLikeRooksVariantGame.
     * 
     * @param players
     * @return GameType representing a everyPieceMovesLikeRooksVariantGame.
     */
    Game everyPieceMovesLikeRooksVariantGame(PlayerPair players);

    /**
     * Use it to get a kingMovesAsQueenVariantGame.
     * 
     * @param players
     * @return GameType representing a kingMovesAsQueenVariantGame.
     */
    Game kingMovesAsQueenVariantGame(PlayerPair players);

}
