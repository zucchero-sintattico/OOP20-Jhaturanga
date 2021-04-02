package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.commons.PlayerPair;
import jhaturanga.commons.TriFunction;
import jhaturanga.model.editor.StringBoard;

public enum GameTypesEnum {

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

    CUSTOM_BOARD_VARIANT("Custom Variant",
            (gameTypeFactory, players, customBoard) -> gameTypeFactory.customizedBoardVariantGame(players, customBoard),
            GameTypeDescription.customizedBoard()),

    /**
     * Chess Problem.
     */
    CHESS_PROBLEM("Chess Problem", (a, b) -> null, null);

    private final String name;
    private BiFunction<GameTypeFactory, PlayerPair, GameType> gameType;
    private TriFunction<GameTypeFactory, PlayerPair, StringBoard, GameType> dynamicGameType;
    private final GameTypeFactory gameTypeFactory = new GameTypeFactoryImpl();
    private final String gameTypeDescription;

    GameTypesEnum(final String name, final BiFunction<GameTypeFactory, PlayerPair, GameType> gameType,
            final String gameTypeDescription) {
        this.name = name;
        this.gameType = gameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    GameTypesEnum(final String name,
            final TriFunction<GameTypeFactory, PlayerPair, StringBoard, GameType> dynamicGameType,
            final String gameTypeDescription) {
        this.name = name;
        this.dynamicGameType = dynamicGameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    public GameType getGameType(final PlayerPair players) {
        return this.gameType.apply(this.gameTypeFactory, players);
    }

    public GameType getDynamicGameType(final PlayerPair players, final StringBoard customBoard) {
        return this.dynamicGameType.apply(this.gameTypeFactory, players, customBoard);
    }

    public String getGameTypeDescription() {
        return this.gameTypeDescription;
    }

    public String getName() {
        return this.name;
    }

}
