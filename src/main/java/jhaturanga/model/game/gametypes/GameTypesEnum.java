package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.commons.Pair;
import jhaturanga.commons.TriFunction;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.player.Player;

public enum GameTypesEnum {
    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pawnHordeVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pawnHordeVariant()),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pawnMovemementVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC_GAME((gameTypeFactory, players) -> gameTypeFactory.classicGame(players.getX(), players.getY()),
            GameTypeDescription.classicGameType()),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pieceSwapVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pieceSwapVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    THREE_COLUMNS_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.threeColumnsVariantGame(players.getX(), players.getY()),
            GameTypeDescription.threeColumnsVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    BOMB_VARIANT((gameTypeFactory, players) -> gameTypeFactory.bombVariantGame(players.getX(), players.getY()),
            GameTypeDescription.bombVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.oneDimensionVariantGame(players.getX(), players.getY()),
            GameTypeDescription.oneDimensionVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ROOK_AND_BISHOP_MOVEMENT_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.rookBishopMovementVariantGame(players.getX(), players.getY()),
            GameTypeDescription.rookBishopMovementVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    CUSTOM_BOARD_VARIANT((gameTypeFactory, players, customBoard) -> gameTypeFactory
            .customizedBoardVariantGame(players.getX(), players.getY(), customBoard),
            GameTypeDescription.customizedBoard());

    private BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType;
    private TriFunction<GameTypeFactory, Pair<Player, Player>, StringBoard, GameType> dynamicGameType;
    private final GameTypeFactory gameTypeFactory = new GameTypeFactoryImpl();
    private final String gameTypeDescription;

    GameTypesEnum(final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType,
            final String gameTypeDescription) {
        this.gameType = gameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    GameTypesEnum(final TriFunction<GameTypeFactory, Pair<Player, Player>, StringBoard, GameType> dynamicGameType,
            final String gameTypeDescription) {
        this.dynamicGameType = dynamicGameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    public GameType getGameType(final Player whitePlayer, final Player blackPlayer) {
        return this.gameType.apply(this.gameTypeFactory, new Pair<>(whitePlayer, blackPlayer));
    }

    public GameType getDynamicGameType(final Player whitePlayer, final Player blackPlayer,
            final StringBoard customBoard) {
        return this.dynamicGameType.apply(this.gameTypeFactory, new Pair<>(whitePlayer, blackPlayer), customBoard);
    }

    public String getGameTypeDescription() {
        return this.gameTypeDescription;
    }

}
