package jhaturanga.model.piece.movement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public abstract class AbstractPieceMovementStrategyFactory implements PieceMovementStrategyFactory {

    /**
     * Single increments are used for movement related calculation.
     */
    protected static final int SINGLE_INCREMENT = 1;
    /**
     * Double increments are used for movement related calculation.
     */
    protected static final int DOUBLE_INCREMENT = 2;

    protected final Set<BoardPosition> fromFunction(final UnaryOperator<BoardPosition> function, final Piece piece,
            final Board board, final int limit) {
        /*
         * The "function.apply" at the seed of the Stream.Iterate is used to skip the
         * first element, that's itself.
         */
        final List<BoardPosition> positions = Stream.iterate(function.apply(piece.getPiecePosition()), function)
                .takeWhile(board::contains)
                .takeWhile(x -> board.getPieceAtPosition(x).isEmpty()
                        || !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                .limit(limit).collect(Collectors.toList());

        final Optional<BoardPosition> pos = positions.stream().filter(i -> board.getPieceAtPosition(i).isPresent()
                && !board.getPieceAtPosition(i).get().getPlayer().equals(piece.getPlayer())).findFirst();

        /*
         * The sublist excludes the last n-th element of the high-endpoint
         */
        return pos.isEmpty() ? new HashSet<>(positions) : new HashSet<>(positions.subList(0, positions.indexOf(pos.get()) + 1));
    }

    /**
     * This is a Template Method. Every movement strategy of the specific piece is
     * implemented by the specific PieceMovementStrategyFactory. Example: the
     * ClassicPieceMovementStrategyFactory or the PawnVariant one.
     */
    @Override
    public final PieceMovementStrategy getPieceMovementStrategy(final Piece piece) {
        switch (piece.getType()) {

        case PAWN:
            return this.getPawnMovementStrategy(piece);
        case ROOK:
            return this.getRookMovementStrategy(piece);
        case KNIGHT:
            return this.getKnightMovementStrategy(piece);
        case BISHOP:
            return this.getBishopMovementStrategy(piece);
        case QUEEN:
            return this.getQueenMovementStrategy(piece);
        case KING:
            return this.getKingMovementStrategy(piece);

        default:
            return null;
        }

    }

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a Pawn
     */
    protected abstract PieceMovementStrategy getPawnMovementStrategy(Piece piece);

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a Rook
     */
    protected abstract PieceMovementStrategy getRookMovementStrategy(Piece piece);

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a Knight
     */
    protected abstract PieceMovementStrategy getKnightMovementStrategy(Piece piece);

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a Bishop
     */
    protected abstract PieceMovementStrategy getBishopMovementStrategy(Piece piece);

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a Queen
     */
    protected abstract PieceMovementStrategy getQueenMovementStrategy(Piece piece);

    /**
     * 
     * @param piece
     * @return PieceMovementStrategy representing the movementStrategy of a King
     */
    protected abstract PieceMovementStrategy getKingMovementStrategy(Piece piece);
}
