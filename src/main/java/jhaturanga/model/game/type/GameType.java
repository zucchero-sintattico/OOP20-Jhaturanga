package jhaturanga.model.game.type;

import jhaturanga.model.game.Game;
import jhaturanga.model.game.factory.GameFactory;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.player.pair.PlayerPair;

/**
 * The types of game.
 */
public enum GameType {

    /**
     * The classic chess.
     */
    CLASSIC("Classic", GameFactory::classicGame, GameTypeDescription.classicGameType()),

    /**
     * The Pawn Horde variant.
     */
    PAWN_HORDE_VARIANT("Pawn Horde", GameFactory::pawnHordeVariantGame, GameTypeDescription.pawnHordeVariant()),

    /**
     * The Pawn Movement variant.
     */
    PAWN_MOVEMENT_VARIANT("Spectacular Pawn", GameFactory::pawnMovemementVariantGame,
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * The Piece Swap variant.
     */
    PIECE_SWAP_VARIANT("Swappiness", GameFactory::pieceSwapVariantGame, GameTypeDescription.pieceSwapVariant()),

    /**
     * The Three Columns variant.
     */
    THREE_COLUMNS_VARIANT("3-Col", GameFactory::threeColumnsVariantGame, GameTypeDescription.threeColumnsVariant()),

    /**
     * The Bomb variant.
     */
    BOMB_VARIANT("Bombastic", GameFactory::bombVariantGame, GameTypeDescription.bombVariant()),

    /**
     * The One Dimension variant.
     */
    ONE_DIMENSION_VARIANT("1-Dimension", GameFactory::oneDimensionVariantGame,
            GameTypeDescription.oneDimensionVariant()),

    /**
     * The Rook And Bishop variant.
     */
    ROOK_AND_BISHOP_MOVEMENT_VARIANT("Rook & Bishop", GameFactory::rookBishopMovementVariantGame,
            GameTypeDescription.rookBishopMovementVariant()),

    /**
     * The Everyone Moves Like A Rook variant.
     */
    EVERYONE_MOVES_LIKE_A_ROOK("EveryRook", GameFactory::everyPieceMovesLikeRooksVariantGame,
            GameTypeDescription.everyoneMovesLikeRooks()),

    /**
     * The King Moves Like Queen variant.
     */
    KING_MOVES_LIKE_QUEEN("QueenK", GameFactory::kingMovesAsQueenVariantGame, GameTypeDescription.kingMovesLikeQueen()),

    /**
     * The Custom Board.
     */
    CUSTOM_BOARD_VARIANT("Custom Variant", null, null),

    /**
     * The Chess Problem.
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

    /**
     * Get a new game instance of this game type.
     * 
     * @param players
     * @return the new game instance
     */
    public Game getGameInstance(final PlayerPair players) {
        return this.gameGeneratorStrategy.generate(this.gameTypeFactory, players);
    }

    /**
     * @return the description of the game type.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the name of the game type.
     */
    public String getName() {
        return this.name;
    }

}
