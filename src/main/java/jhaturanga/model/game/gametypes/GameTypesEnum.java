package jhaturanga.model.game.gametypes;

import java.util.function.BiFunction;

import jhaturanga.model.player.Player;

public enum GameTypesEnum {
    /**
     * Every time used it returns a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT((whitePlayer, blackPlayer) -> new PawnHordeVariantGameType(whitePlayer, blackPlayer)),
    /**
     * Every time used it returns a new instance of the PAWN_MOVEMENT_VARIANT
     * GameType.
     */
    PAWN_MOVEMENT_VARIANT((whitePlayer, blackPlayer) -> new PawnMovementVariantGameType(whitePlayer, blackPlayer)),

    /**
     * Every time used it returns a new instance of the PAWN_MOVEMENT_VARIANT
     * GameType.
     */
    CLASSIC_GAME((whitePlayer, blackPlayer) -> new ClassicGameType(whitePlayer, blackPlayer)),

    /**
     * Every time used it returns a new instance of the PIECE_SWAP_VARIANT GameType.
     */
    PIECE_SWAP_VARIANT((whitePlayer, blackPlayer) -> new PieceSwapVariantGameType(whitePlayer, blackPlayer));

    private final BiFunction<Player, Player, GameType> gameType;

    GameTypesEnum(final BiFunction<Player, Player, GameType> gameType) {
        this.gameType = gameType;
    }

    public GameType getNewGameType(final Player whitePlayer, final Player blackPlayer) {
        return this.gameType.apply(whitePlayer, blackPlayer);
    }
}
