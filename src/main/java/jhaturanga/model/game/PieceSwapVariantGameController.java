package jhaturanga.model.game;

import jhaturanga.commons.PlayerPair;
import jhaturanga.model.board.Board;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategies;

public class PieceSwapVariantGameController extends ClassicGameController {

    public PieceSwapVariantGameController(final Board board, final PieceMovementStrategies pieceMovementStrategies,
            final PlayerPair players) {
        super(board, pieceMovementStrategies, players);
    }

    /**
     * The PieceSwapVariantGameType can't rely fully on the ClassicGameController
     * due to some differences in the draw conditions caused by insufficient
     * material.
     * 
     * @return true only if the game is in a status where there are only to kings,
     *         any other situation may lead to check-mate.
     */
    @Override
    protected final boolean insufficientMaterialToWin() {
        return this.boardState().getPiecesStatus().stream().filter(i -> !i.getType().equals(PieceType.KING))
                .count() == 0;

    }

}
