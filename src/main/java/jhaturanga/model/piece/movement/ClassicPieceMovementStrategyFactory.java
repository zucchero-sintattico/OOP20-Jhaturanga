package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.commons.TriFunction;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public class ClassicPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    private boolean canCastle = true;

    /**
     * If you need to call the fromFunction method twice for specular directions use
     * this TriFunction instead.
     */

    private final BiFunction<BoardPosition, Pair<Integer, Integer>, BoardPosition> sumBoardPosWithPair = (pos,
            pair) -> new BoardPositionImpl(pos.getX() + pair.getX(), pos.getY() + pair.getY());

    private final Function<Pair<Integer, Integer>, UnaryOperator<BoardPosition>> unaryCreator = (
            axis) -> (p) -> this.sumBoardPosWithPair.apply(p, axis);

    private final TriFunction<Piece, Vectors, Board, Set<BoardPosition>> specularNoLimitDirection = (piece, axis,
            board) -> Stream.concat(
                    this.fromFunction(this.unaryCreator.apply(axis.getAxis()), piece, board,
                            board.getColumns() + board.getRows()).stream(),
                    this.fromFunction(this.unaryCreator.apply(axis.getOpposite()), piece, board,
                            board.getColumns() + board.getRows()).stream())
                    .collect(Collectors.toSet());

    /**
     * This method is used to get the movement strategy of a Pawn. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    public PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        return (final Board board) -> {

            final Set<BoardPosition> positions = new HashSet<>();
            /*
             * The increment of the piece. The white goes from bottom to up so the row is
             * incremented by 1 The black goes from top to bottom so the row is incremented
             * by -1
             */
            final int yIncrement = piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? SINGLE_INCREMENT
                    : -SINGLE_INCREMENT;

            final Predicate<BoardPosition> onlyIfEnemyIsPresent = x -> board.getPieceAtPosition(x).isPresent()
                    && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer());

            List.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(xIncrement -> {
                positions.addAll(this
                        .fromFunction(pos -> new BoardPositionImpl(pos.getX() + xIncrement, pos.getY() + yIncrement),
                                piece, board, SINGLE_INCREMENT)
                        .stream().filter(onlyIfEnemyIsPresent).collect(Collectors.toSet()));
            });

            final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX(),
                    piece.getPiecePosition().getY() + yIncrement);
            if (board.contains(upFront) && board.getPieceAtPosition(upFront).isEmpty()) {
                positions.add(upFront);
            }

            // Check the initial double movement for white's pawns

            if (!piece.hasAlreadyBeenMoved() && board.getPieceAtPosition(upFront).isEmpty()) {
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + yIncrement),
                        piece, board, DOUBLE_INCREMENT));
            }

            return Collections.unmodifiableSet(positions);
        };
    }

    /**
     * This method is used to get the movement strategy of a Rook. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            return Stream
                    .concat(this.specularNoLimitDirection.apply(piece, Vectors.VERTICAL, board).stream(),
                            this.specularNoLimitDirection.apply(piece, Vectors.HORIZONTAL, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a Knight. It's specific
     * of the kind of variant and GameType.
     */
    @Override
    public PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT)
                    .forEach(x -> Set.of(DOUBLE_INCREMENT, -DOUBLE_INCREMENT).forEach(y -> {
                        positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + x, pos.getY() + y),
                                piece, board, SINGLE_INCREMENT));
                        positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + y, pos.getY() + x),
                                piece, board, SINGLE_INCREMENT));
                    }));
            return Collections.unmodifiableSet(positions);
        };
    }

    /**
     * This method is used to get the movement strategy of a Bishop. It's specific
     * of the kind of variant and GameType.
     */
    @Override
    public PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            return Stream
                    .concat(this.specularNoLimitDirection.apply(piece, Vectors.TOP_LEFT_BOT_RIGHT, board).stream(),
                            this.specularNoLimitDirection.apply(piece, Vectors.TOP_RIGHT_BOT_LEFT, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a Queen. It's specific of
     * the kind of variant and GameType.
     */
    // TODO: CITA NELLA RELAZIONE
    @Override
    public PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            return Stream
                    .concat(this.getBishopMovementStrategy(piece).getPossibleMoves(board).stream(),
                            this.getRookMovementStrategy(piece).getPossibleMoves(board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a King. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    public PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getQueenMovementStrategy(piece).getPossibleMoves(board).stream().filter(i -> this
                    .distanceBetweenBoardPositions(piece.getPiecePosition(), i).getX() <= SINGLE_INCREMENT
                    && this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getY() <= SINGLE_INCREMENT)
                    .collect(Collectors.toSet()));

            // Short Castle
            if (this.canCastle && !piece.hasAlreadyBeenMoved()) {
                positions.addAll(Stream.concat(
                        this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - SINGLE_INCREMENT, pos.getY()),
                                piece, board, DOUBLE_INCREMENT).stream(),
                        this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + SINGLE_INCREMENT, pos.getY()),
                                piece, board, DOUBLE_INCREMENT).stream())
                        .collect(Collectors.toSet()));
            }
            return Collections.unmodifiableSet(positions);
        };
    }

    /**
     * Use this method to get the distance between two BoardPosition as a
     * Vector-BoardPosition.
     * 
     * @param p1 represents BoardPosition1
     * @param p2 represents BoardPosition2
     * @return BoardPosition represents as a vector the distance between two
     *         boardPositions.
     */
    protected BoardPosition distanceBetweenBoardPositions(final BoardPosition p1, final BoardPosition p2) {
        return new BoardPositionImpl(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

    @Override
    public final void setCanCastle(final boolean canCastle) {
        this.canCastle = canCastle;
    }

    @Override
    public final boolean canCastle() {
        return this.canCastle;
    }

}
