package jhaturanga.model.game.type;

import jhaturanga.model.game.Game;
import jhaturanga.model.game.factory.GameFactory;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.player.pair.PlayerPair;

public enum GameType {

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC("Classic", GameFactory::classicGame, GameTypeDescription.classicGameType()),

    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT("Pawn Horde", GameFactory::pawnHordeVariantGame, GameTypeDescription.pawnHordeVariant()),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT("Spectacular Pawn", GameFactory::pawnMovemementVariantGame,
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT("Swappiness", GameFactory::pieceSwapVariantGame, GameTypeDescription.pieceSwapVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    THREE_COLUMNS_VARIANT("3-Col", GameFactory::threeColumnsVariantGame, GameTypeDescription.threeColumnsVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    BOMB_VARIANT("Bombastic", GameFactory::bombVariantGame, GameTypeDescription.bombVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT("1-Dimension", GameFactory::oneDimensionVariantGame,
            GameTypeDescription.oneDimensionVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ROOK_AND_BISHOP_MOVEMENT_VARIANT("Rook & Bishop", GameFactory::rookBishopMovementVariantGame,
            GameTypeDescription.rookBishopMovementVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    EVERYONE_MOVES_LIKE_A_ROOK("EveryRook", GameFactory::everyPieceMovesLikeRooksVariantGame,
            GameTypeDescription.everyoneMovesLikeRooks()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    KING_MOVES_LIKE_QUEEN("QueenK", GameFactory::kingMovesAsQueenVariantGame, GameTypeDescription.kingMovesLikeQueen()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */

    CUSTOM_BOARD_VARIANT("Custom Variant", null, null),

    /**
     * Chess Problem.
     */
    CHESS_PROBLEM("Chess Problem", null, null);

    private final String name;
    private final GameGenerationStrategy gameGeneratorStrategy;
    private final GameFactory gameTypeFactory = new GameFactoryImpl();
    private final String description;

    GameType(final String name, final GameGenerationStrategy gameTypeGeneratorStrategy,
            final String gameTypeDescription) {
        this.name = name;
        this.gameGeneratorStrategy = gameTypeGeneratorStrategy;
        this.description = gameTypeDescription;
    }

    public Game getGameInstance(final PlayerPair players) {
        return this.gameGeneratorStrategy.generate(this.gameTypeFactory, players);
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

}
