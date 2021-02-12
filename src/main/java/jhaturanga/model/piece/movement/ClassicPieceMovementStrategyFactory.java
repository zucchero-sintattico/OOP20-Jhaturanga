package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public final class ClassicPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    private static final int SINGLE_INCREMENT = 1;
    private static final int DOUBLE_INCREMENT = 2;

    @Override
    public PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        // tab
        return (final Board board) -> {

            final Set<BoardPosition> positions = new HashSet<>();
            /*
             * The increment of the piece. The white goes from bottom to up so the row is
             * incremented by 1 The black goes from top to bottom so the row is incremented
             * by -1
             */
            final int increment = piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? SINGLE_INCREMENT : -SINGLE_INCREMENT;
            positions.addAll(this
                    .fromFunction(
                            pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() + increment), piece, board, SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            positions.addAll(this
                    .fromFunction(
                            pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() + increment), piece, board, SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX(),
                    piece.getPiecePosition().getY() + increment);

            if (board.getPieceAtPosition(upFront).isEmpty()) {
                positions.add(upFront);
            }
            return Collections.unmodifiableSet(positions);
        };
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(increment -> {
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + increment), piece, board,
                        board.getRows()));
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + increment, pos.getY()), piece, board,
                        board.getRows()));
            });
            return Collections.unmodifiableSet(positions);
        };
    }

    @Override
    public PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(x -> Set.of(DOUBLE_INCREMENT, -DOUBLE_INCREMENT).forEach(y -> {
                positions
                        .addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + x, pos.getY() + y), piece, board, 1));
                positions
                        .addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + y, pos.getY() + x), piece, board, 1));
            }));
            return Collections.unmodifiableSet(positions);
        };
    }

    @Override
    public PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(x -> Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(y -> {
                positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + x, pos.getY() + y), piece, board,
                        board.getRows() + board.getColumns()));
            }));
            return Collections.unmodifiableSet(positions);
        };
    }

    // TODO: CITA NELLA RELAZIONE
    @Override
    public PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getBishopMovementStrategy(piece).getPossibleMoves(board));
            positions.addAll(this.getRookMovementStrategy(piece).getPossibleMoves(board));
            return Collections.unmodifiableSet(positions);
        };
    }

    @Override
    public PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
        return (final Board board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getQueenMovementStrategy(piece).getPossibleMoves(board).stream()
                    .filter(i -> this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getX() <= SINGLE_INCREMENT
                            && this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getY() <= SINGLE_INCREMENT)
                    .collect(Collectors.toSet()));
            return Collections.unmodifiableSet(positions);
        };
    }

    // TODO: Non dovrebbe tornare una BoardPosition in realtà, nonostante funzioni
    private BoardPosition distanceBetweenBoardPositions(final BoardPosition p1, final BoardPosition p2) {
        return new BoardPositionImpl(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

}
