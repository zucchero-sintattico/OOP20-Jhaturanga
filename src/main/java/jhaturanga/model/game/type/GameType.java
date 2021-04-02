package jhaturanga.model.game.type;

import jhaturanga.model.game.Game;
import jhaturanga.model.game.factory.GameFactory;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.player.PlayerPair;

public enum GameType {

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC_GAME("Classic", (gameTypeFactory, players) -> gameTypeFactory.classicGame(players),
            GameTypeDescription.classicGameType()),

    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT("Pawn Horde", (gameTypeFactory, players) -> gameTypeFactory.pawnHordeVariantGame(players),
            GameTypeDescription.pawnHordeVariant()),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT("Spectacular Pawn",
            (gameTypeFactory, players) -> gameTypeFactory.pawnMovemementVariantGame(players),
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT("Swappiness", (gameTypeFactory, players) -> gameTypeFactory.pieceSwapVariantGame(players),
            GameTypeDescription.pieceSwapVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    THREE_COLUMNS_VARIANT("3-Col", (gameTypeFactory, players) -> gameTypeFactory.threeColumnsVariantGame(players),
            GameTypeDescription.threeColumnsVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    BOMB_VARIANT("Bombastic", (gameTypeFactory, players) -> gameTypeFactory.bombVariantGame(players),
            GameTypeDescription.bombVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT("1-Dimension", (gameTypeFactory, players) -> gameTypeFactory.oneDimensionVariantGame(players),
            GameTypeDescription.oneDimensionVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ROOK_AND_BISHOP_MOVEMENT_VARIANT("Rook & Bishop",
            (gameTypeFactory, players) -> gameTypeFactory.rookBishopMovementVariantGame(players),
            GameTypeDescription.rookBishopMovementVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    EVERYONE_MOVES_LIKE_A_ROOK("EveryRook",
            (gameTypeFactory, players) -> gameTypeFactory.everyPieceMovesLikeRooksVariantGame(players),
            GameTypeDescription.everyoneMovesLikeRooks()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    KING_MOVES_LIKE_QUEEN("QueenK", (gameTypeFactory, players) -> gameTypeFactory.kingMovesAsQueenVariantGame(players),
            GameTypeDescription.kingMovesLikeQueen()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */

    CUSTOM_BOARD_VARIANT("Custom Variant", null, null),

    /**
     * Chess Problem.
     */
    CHESS_PROBLEM("Chess Problem", null, null);

    private final String name;
    private final GameGeneratorStrategy gameTypeGeneratorStrategy;
    private final GameFactory gameTypeFactory = new GameFactoryImpl();
    private final String gameTypeDescription;

    GameType(final String name, final GameGeneratorStrategy gameTypeGeneratorStrategy,
            final String gameTypeDescription) {
        this.name = name;
        this.gameTypeGeneratorStrategy = gameTypeGeneratorStrategy;
        this.gameTypeDescription = gameTypeDescription;
    }

    public Game getGeneratedGameType(final PlayerPair players) {
        return this.gameTypeGeneratorStrategy.generate(this.gameTypeFactory, players);
    }

    public String getGameTypeDescription() {
        return this.gameTypeDescription;
    }

    public String getName() {
        return this.name;
    }

}
