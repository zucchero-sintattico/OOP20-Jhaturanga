package jhaturanga.model.game.gametypes;

import java.util.function.Supplier;

import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

public enum GameTypesEnum {
    /**
     * Every time used it returns a new instance of the PAWN_HORDE_VARIANT GameType.
     */
    PAWN_HORDE_VARIANT(
            () -> new PawnHordeVariantGameType(new PlayerImpl(PlayerColor.WHITE), new PlayerImpl(PlayerColor.BLACK))),
    /**
     * Every time used it returns a new instance of the PAWN_MOVEMENT_VARIANT
     * GameType.
     */
    PAWN_MOVEMENT_VARIANT(() -> new PawnMovementVariantGameType(new PlayerImpl(PlayerColor.WHITE),
            new PlayerImpl(PlayerColor.BLACK))),

    /**
     * Every time used it returns a new instance of the PAWN_MOVEMENT_VARIANT
     * GameType.
     */
    CLASSIC_GAME(() -> new ClassicGameType(new PlayerImpl(PlayerColor.WHITE), new PlayerImpl(PlayerColor.BLACK)));

    private final Supplier<GameType> gameType;

    GameTypesEnum(final Supplier<GameType> gameType) {
        this.gameType = gameType;
    }

    public GameType getNewGameType() {
        return this.gameType.get();
    }
}
