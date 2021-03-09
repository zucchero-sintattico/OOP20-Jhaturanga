package jhaturanga.model.game;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class PieceSwapVariantGameController extends ClassicGameController {

    public PieceSwapVariantGameController(final Board board, final PieceMovementStrategyFactory pieceMovementStrategies,
            final List<Player> players) {
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
        final Supplier<Stream<Piece>> boardStreamWithoutKings = () -> this.boardState().getBoardState().stream()
                .filter(i -> !i.getType().equals(PieceType.KING));
        return boardStreamWithoutKings.get().count() == 0;
    }

}
