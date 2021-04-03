package jhaturanga.model.game.type;

import jhaturanga.model.game.Game;
import jhaturanga.model.game.factory.GameFactory;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.player.PlayerPair;

public enum GameType {

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC_GAME("Classic", (gameFactory, players) -> gameFactory.classicGame(players),
            GameTypeDescription.classicGameType()),

    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT("Pawn Horde", (gameFactory, players) -> gameFactory.pawnHordeVariantGame(players),
            GameTypeDescription.pawnHordeVariant()),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT("Spectacular Pawn", (gameFactory, players) -> gameFactory.pawnMovemementVariantGame(players),
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT("Swappiness", (gameFactory, players) -> gameFactory.pieceSwapVariantGame(players),
            GameTypeDescription.pieceSwapVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    THREE_COLUMNS_VARIANT("3-Col", (gameFactory, players) -> gameFactory.threeColumnsVariantGame(players),
            GameTypeDescription.threeColumnsVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    BOMB_VARIANT("Bombastic", (gameFactory, players) -> gameFactory.bombVariantGame(players),
            GameTypeDescription.bombVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT("1-Dimension", (gameFactory, players) -> gameFactory.oneDimensionVariantGame(players),
            GameTypeDescription.oneDimensionVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ROOK_AND_BISHOP_MOVEMENT_VARIANT("Rook & Bishop",
            (gameFactory, players) -> gameFactory.rookBishopMovementVariantGame(players),
            GameTypeDescription.rookBishopMovementVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    EVERYONE_MOVES_LIKE_A_ROOK("EveryRook",
            (gameFactory, players) -> gameFactory.everyPieceMovesLikeRooksVariantGame(players),
            GameTypeDescription.everyoneMovesLikeRooks()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    KING_MOVES_LIKE_QUEEN("QueenK", (gameFactory, players) -> gameFactory.kingMovesAsQueenVariantGame(players),
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
    private final GameGeneratorStrategy gameGeneratorStrategy;
    private final GameFactory gameTypeFactory = new GameFactoryImpl();
    private final String description;

    GameType(final String name, final GameGeneratorStrategy gameTypeGeneratorStrategy,
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
