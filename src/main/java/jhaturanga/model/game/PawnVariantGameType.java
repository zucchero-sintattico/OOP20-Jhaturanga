package jhaturanga.model.game;

import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class PawnVariantGameType extends ClassicGameType {
    public PawnVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        super(whitePlayer, blackPlayer, new PawnVariantPieceMovementStrategyFactory());
    }
}
