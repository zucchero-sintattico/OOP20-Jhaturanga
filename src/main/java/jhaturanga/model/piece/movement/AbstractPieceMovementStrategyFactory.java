package jhaturanga.model.piece.movement;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.commons.TriFunction;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;

public abstract class AbstractPieceMovementStrategyFactory implements PieceMovementStrategyFactory {

    private boolean canCastle = true;

    /**
     * Single increments are used for movement related calculation.
     */
    protected static final int SINGLE_INCREMENT = 1;
    /**
     * Double increments are used for movement related calculation.
     */
    protected static final int DOUBLE_INCREMENT = 2;

    private final BiFunction<BoardPosition, Pair<Integer, Integer>, BoardPosition> sumBoardPosWithPair = (pos,
            pair) -> new BoardPositionImpl(pos.getX() + pair.getX(), pos.getY() + pair.getY());

    private final Function<Pair<Integer, Integer>, UnaryOperator<BoardPosition>> unaryCreator = (
            axis) -> (p) -> this.sumBoardPosWithPair.apply(p, axis);
    /**
     * If you need to call the fromFunction method twice for specular directions use
     * this TriFunction specularNoLimitDirection instead.
     */
    private final TriFunction<Piece, Vectors, Board, Set<BoardPosition>> specularNoLimitDirection = (piece, axis,
            board) -> Stream.concat(
                    this.fromFunction(this.unaryCreator.apply(axis.getAxis()), piece, board,
                            board.getColumns() + board.getRows()).stream(),
                    this.fromFunction(this.unaryCreator.apply(axis.getOpposite()), piece, board,
                            board.getColumns() + board.getRows()).stream())
                    .collect(Collectors.toSet());
    /**
     * This Map is used to get a function that maps a piece to it's respective
     * MovementStrategy, this was made to avoid the use of a switch conditional
     * statement.
     */

    private final EnumMap<PieceType, Function<Piece, PieceMovementStrategy>> fromPieceTypeToStrategy = new EnumMap<>(
            PieceType.class) {
        private static final long serialVersionUID = 1L;
        {
            put(PieceType.PAWN, AbstractPieceMovementStrategyFactory.this::getPawnMovementStrategy);
            put(PieceType.ROOK, AbstractPieceMovementStrategyFactory.this::getRookMovementStrategy);
            put(PieceType.KNIGHT, AbstractPieceMovementStrategyFactory.this::getKnightMovementStrategy);
            put(PieceType.BISHOP, AbstractPieceMovementStrategyFactory.this::getBishopMovementStrategy);
            put(PieceType.QUEEN, AbstractPieceMovementStrategyFactory.this::getQueenMovementStrategy);
            put(PieceType.KING, AbstractPieceMovementStrategyFactory.this::getKingMovementStrategy);
        }
    };

    protected final Set<BoardPosition> fromFunction(final UnaryOperator<BoardPosition> function, final Piece piece,
            final Board board, final int limit) {
        /*
         * The "function.apply" at the seed of the Stream.Iterate is used to skip the
         * first element, that's itself, in fact a piece can't have as a possible move
         * it's original position.
         */
        final List<BoardPosition> positions = Stream.iterate(function.apply(piece.getPiecePosition()), function)
                .takeWhile(board::contains)
                .takeWhile(x -> board.getPieceAtPosition(x).isEmpty()
                        || !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                .limit(limit).collect(Collectors.toList());

        final Optional<BoardPosition> pos = positions.stream().filter(i -> board.getPieceAtPosition(i).isPresent()
                && !board.getPieceAtPosition(i).get().getPlayer().equals(piece.getPlayer())).findFirst();
        /*
         * The sublist excludes the last n-th element of the high-endpoint, for this
         * reason we need to add 1.
         */
        return pos.isEmpty() ? new HashSet<>(positions)
                : new HashSet<>(positions.subList(0, positions.indexOf(pos.get()) + SINGLE_INCREMENT));
    }

    /**
     * This is a Template Method. Every movement strategy of the specific piece is
     * implemented by the specific PieceMovementStrategyFactory. Example: the
     * ClassicPieceMovementStrategyFactory or the PawnVariant one.
     */
    @Override
    public final PieceMovementStrategy getPieceMovementStrategy(final Piece piece) {
        return this.fromPieceTypeToStrategy.computeIfAbsent(piece.getType(), k -> this::emptyMovementStrategy)
                .apply(piece);
    }

    /**
     * 
     * @param piece - the piece that for some unknown reason may not be part of the
     *              fromPieceStrategy map.
     * @return PieceMovementStrategy representing the movementStrategy of a piece
     *         who's MovementStrategy is not defined for some reason.
     */
    private PieceMovementStrategy emptyMovementStrategy(final Piece piece) {
        return (board) -> Set.of(piece.getPiecePosition());
    };

    /**
     * 
     * @return TriFunction<Piece, Vectors, Board, Set<BoardPosition>> used by
     *         sub-classes to more easily access the fromFunction method
     */
    protected final TriFunction<Piece, Vectors, Board, Set<BoardPosition>> getSpecularNoLimitDirection() {
        return this.specularNoLimitDirection;
    }

    @Override
    public final void setCanCastle(final boolean canCastle) {
        this.canCastle = canCastle;
    }

    @Override
    public final boolean canCastle() {
        return this.canCastle;
    }

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a Pawn
     */
    protected abstract PieceMovementStrategy getPawnMovementStrategy(Piece piece);

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a Rook
     */
    protected abstract PieceMovementStrategy getRookMovementStrategy(Piece piece);

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a Knight
     */
    protected abstract PieceMovementStrategy getKnightMovementStrategy(Piece piece);

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a Bishop
     */
    protected abstract PieceMovementStrategy getBishopMovementStrategy(Piece piece);

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a Queen
     */
    protected abstract PieceMovementStrategy getQueenMovementStrategy(Piece piece);

    /**
     * 
     * @return PieceMovementStrategy representing the movementStrategy of a King
     */
    protected abstract PieceMovementStrategy getKingMovementStrategy(Piece piece);
}
