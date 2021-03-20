package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public class ClassicPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    private boolean canCastle = true;

//    private final TriFunction<Set<UnaryOperator<BoardPosition>>, Piece, Board, Set<BoardPosition>> function;

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
            final Side side = piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? Side.UP : Side.DOWN;

            positions.addAll(this
                    .fromFunction(Directions.DIAG_BOT_LEFT_TOP_RIGHT
                            .getDirectionOperator(piece.getPiecePosition(), Side.RIGHT), piece, board, SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            positions.addAll(this
                    .fromFunction(Directions.DIAG_TOP_LEFT_BOT_RIGHT
                            .getDirectionOperator(piece.getPiecePosition(), Side.LEFT), piece, board, SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX(),
                    piece.getPiecePosition().getY() + side.getSide());
            if (board.contains(upFront) && board.getPieceAtPosition(upFront).isEmpty()) {
                positions.add(upFront);
            }

            // Check the initial double movement for white's pawns

            if (!piece.hasAlreadyBeenMoved() && board.getPieceAtPosition(upFront).isEmpty()) {
                positions.addAll(this.fromFunction(
                        pos -> new BoardPositionImpl(pos.getX(), pos.getY() + side.getSide()), piece, board, 2));
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
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(increment -> {
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + increment),
                        piece, board, board.getRows()));
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + increment, pos.getY()),
                        piece, board, board.getColumns()));
            });
            return Collections.unmodifiableSet(positions);
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
                                piece, board, 1));
                        positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + y, pos.getY() + x),
                                piece, board, 1));
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
            final Set<BoardPosition> positions = new HashSet<>();

            positions.addAll(this.fromFunction(
                    Directions.DIAG_BOT_LEFT_TOP_RIGHT.getDirectionOperator(piece.getPiecePosition(), Side.DOWN), piece,
                    board, board.getColumns() + board.getRows()));
            positions.addAll(this.fromFunction(
                    Directions.DIAG_TOP_LEFT_BOT_RIGHT.getDirectionOperator(piece.getPiecePosition(), Side.UP), piece,
                    board, board.getColumns() + board.getRows()));
            return Collections.unmodifiableSet(positions);
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
            if (!piece.hasAlreadyBeenMoved() && this.canCastle) {
                positions.addAll(Stream.concat(
                        this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY()), piece, board, 2)
                                .stream(),
                        this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY()), piece, board, 2)
                                .stream())
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
     * @return BoardPosition represents as a Vector the distance between two
     *         boardPositions.
     */
    protected BoardPosition distanceBetweenBoardPositions(final BoardPosition p1, final BoardPosition p2) {
        return new BoardPositionImpl(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

    @Override
    public final void setCanCastle(final boolean canCastle) {
        this.canCastle = canCastle;
    }

}
