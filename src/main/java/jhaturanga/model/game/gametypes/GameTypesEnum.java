package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.commons.Pair;
import jhaturanga.model.player.Player;

public enum GameTypesEnum {
    /**
     * Used to return a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pawnHordeVariantGame(players.getX(), players.getY())),
    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    PAWN_MOVEMENT_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pawnMovemementVariantGame(players.getX(), players.getY())),

    /**
     * Used to return a new instance of the PAWN_MOVEMENT_VARIANT GameType.
     */
    CLASSIC_GAME((gameTypeFactory, players) -> gameTypeFactory.classicGameType(players.getX(), players.getY())),

    /**
     * Used to return a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT(
            (gameTypeFactory, players) -> gameTypeFactory.pieceSwapVariantGame(players.getX(), players.getY()));

    private final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType;
    private final GameTypeFactory gameTypeFactory = new GameTypeFactoryImpl();

    GameTypesEnum(final BiFunction<GameTypeFactory, Pair<Player, Player>, GameType> gameType) {
        this.gameType = gameType;
    }

    public GameType getNewGameType(final Player whitePlayer, final Player blackPlayer) {
        return this.gameType.apply(this.gameTypeFactory, new Pair<>(whitePlayer, blackPlayer));
    }
}
