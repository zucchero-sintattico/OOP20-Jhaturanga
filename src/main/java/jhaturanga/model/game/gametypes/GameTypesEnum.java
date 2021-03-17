package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.commons.Pair;
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
     * Used to return a new instance of the ONE_DIMENSION_VARIANT GameType.
     */
    ONE_DIMENSION_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.oneDimensionVariantGame(players.getX(), players.getY()),
            GameTypeDescription.oneDimensionVariant());

    private final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType;
    private final GameTypeFactory gameTypeFactory = new GameTypeFactoryImpl();
    private final String gameTypeDescription;

    GameTypesEnum(final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType,
            final String gameTypeDescription) {
        this.gameType = gameType;
        this.gameTypeDescription = gameTypeDescription;
    }

    public GameType getGameType(final Player whitePlayer, final Player blackPlayer) {
        return this.gameType.apply(this.gameTypeFactory, new Pair<>(whitePlayer, blackPlayer));
    }

    public String getGameTypeDescription() {
        return this.gameTypeDescription;
    }

}
