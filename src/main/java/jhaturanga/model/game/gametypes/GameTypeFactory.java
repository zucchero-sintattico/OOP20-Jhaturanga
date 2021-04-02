package jhaturanga.model.game.gametypes;

import jhaturanga.commons.PlayerPair;
import jhaturanga.model.chessproblems.ChessProblem;
import jhaturanga.model.editor.StringBoard;

public interface GameTypeFactory {

    /**
     * Use it to get a classicGameType.
     * 
     * @param players
     * @return GameType representing a classicGameType.
     */
    GameType classicGame(PlayerPair players);

    /**
     * Use it to get a pawnHordeVariantGame.
     * 
     * @param players
     * @return GameType representing a pawnHordeVariantGame.
     */
    GameType pawnHordeVariantGame(PlayerPair players);

    /**
     * Use it to get a pieceSwapVariantGame.
     * 
     * @param players
     * @return GameType representing a pieceSwapVariantGame.
     */
    GameType pieceSwapVariantGame(PlayerPair players);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param players
     * @return GameType representing a pawnMovemementVariantGame.
     */
    GameType pawnMovemementVariantGame(PlayerPair players);

    /**
     * Use it to get a pawnMovemementVariantGame.
     * 
     * @param players
     * @return GameType representing a threeColumnsVariantGame.
     */
    GameType threeColumnsVariantGame(PlayerPair players);

    /**
     * Use it to get a oneDimensionVariantGame.
     * 
     * @param players
     * @return GameType representing a oneDimensionVariantGame.
     */
    GameType oneDimensionVariantGame(PlayerPair players);

    /**
     * Use it to get a rookBishopMovementVariantGame.
     * 
     * @param players
     * @return GameType representing a rookBishopMovementVariantGame.
     */
    GameType rookBishopMovementVariantGame(PlayerPair players);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param players
     * @return GameType representing a bombVariantGame.
     */
    GameType bombVariantGame(PlayerPair players);

    /**
     * Use it to get a bombVariantGame.
     * 
     * @param players
     * @param chessProblem - the problem chosen to be played.
     * @return GameType representing a bombVariantGame.
     */
    GameType chessProblemGameType(PlayerPair players, ChessProblem chessProblem);

    /**
     * Use it to get a customizedBoardVariantGame.
     * 
     * @param players
     * @param startingBoardInfo - represents the board to build with the
     *                          BoardBuilderFactory. The board is a StringBoard.
     * @return GameType representing a customizedBoardVariantGame.
     */
    GameType customizedBoardVariantGame(PlayerPair players, StringBoard startingBoardInfo);

    /**
     * Use it to get a everyPieceMovesLikeRooksVariantGame.
     * 
     * @param players
     * @return GameType representing a everyPieceMovesLikeRooksVariantGame.
     */
    GameType everyPieceMovesLikeRooksVariantGame(PlayerPair players);

    /**
     * Use it to get a kingMovesAsQueenVariantGame.
     * 
     * @param players
     * @return GameType representing a kingMovesAsQueenVariantGame.
     */
    GameType kingMovesAsQueenVariantGame(PlayerPair players);

}
