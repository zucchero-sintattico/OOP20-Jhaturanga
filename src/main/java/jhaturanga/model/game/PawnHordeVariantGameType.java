package jhaturanga.model.game;

import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class PawnHordeVariantGameType extends ClassicGameType {

    public PawnHordeVariantGameType(Player whitePlayer, Player blackPlayer) {
        super(whitePlayer, blackPlayer, new ClassicPieceMovementStrategyFactory());
    }

}
