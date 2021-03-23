package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.commons.Pair;
import jhaturanga.model.editor.StringBoard;
import jhaturanga.model.player.Player;

public enum GameTypesEnum {

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC_GAME("Classic", (gameTypeFactory, players) -> gameTypeFactory.classicGame(players.getX(), players.getY()),
            GameTypeDescription.classicGameType()),

    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT("Pawn Horde",
            (gameTypeFactory, players) -> gameTypeFactory.pawnHordeVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pawnHordeVariant()),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT("Spectacular Pawn",
            (gameTypeFactory, players) -> gameTypeFactory.pawnMovemementVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pawnMovemementVariant()),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT("Swappiness",
            (gameTypeFactory, players) -> gameTypeFactory.pieceSwapVariantGame(players.getX(), players.getY()),
            GameTypeDescription.pieceSwapVariant()),

    /**
     * Used to return a new instance of the THREE_COLUMNS_VARIANT GameType.
     */
    THREE_COLUMNS_VARIANT("3-Col",
            (gameTypeFactory, players) -> gameTypeFactory.threeColumnsVariantGame(players.getX(), players.getY()),
            GameTypeDescription.threeColumnsVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT("1-Dimension",
            (gameTypeFactory, players) -> gameTypeFactory.oneDimensionVariantGame(players.getX(), players.getY()),
            GameTypeDescription.oneDimensionVariant()),

    /**
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    CUSTOM_BOARD_VARIANT(
            "Custom Variant", (gameTypeFactory, players, customBoard) -> gameTypeFactory
                    .customizedBoardVariantGame(players.getX(), players.getY(), customBoard),
            GameTypeDescription.customizedBoard());

    private final String name;
    private BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType;
    private TriFunction<GameTypeFactory, Pair<Player, Player>, StringBoard, GameType> dynamicGameType;
    private GameTypeFactory gameTypeFactory = new GameTypeFactoryImpl();
    private String gameTypeDescription;

    GameTypesEnum(final String name, final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType,
            final String gameTypeDescription) {
        this.name = name;
        this.gameType = gameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    GameTypesEnum(final String name,
            final TriFunction<GameTypeFactory, Pair<Player, Player>, StringBoard, GameType> dynamicGameType,
            final String gameTypeDescription) {
        this.name = name;
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

    public String getName() {
        return this.name;
    }

}
