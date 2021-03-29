package jhaturanga.model.piece.movement;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.PlayerColor;

public class ClassicPieceMovementStrategies extends AbstractPieceMovementStrategies {

    /**
     * This method is used to get the movement strategy of a Pawn. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    protected PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
        return (board) -> {

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
                positions.addAll(super.getDestinationsFromFunction(
                        pos -> new BoardPositionImpl(pos.getX() + xIncrement, pos.getY() + yIncrement), piece, board,
                        SINGLE_INCREMENT).stream().filter(onlyIfEnemyIsPresent).collect(Collectors.toSet()));
            });

            final BoardPosition front = new BoardPositionImpl(piece.getPiecePosition().getX(),
                    piece.getPiecePosition().getY() + yIncrement);
            if (board.contains(front) && board.getPieceAtPosition(front).isEmpty()) {
                positions.add(front);
            }

            // Check the initial double movement for white's pawns

            if (!piece.hasAlreadyBeenMoved() && board.getPieceAtPosition(front).isEmpty() && board
                    .getPieceAtPosition(new BoardPositionImpl(front.getX(), front.getY() + yIncrement)).isEmpty()) {
                positions.addAll(super.getDestinationsFromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + yIncrement),
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
    protected PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
        return (board) -> {
            return Stream
                    .concat(super.getSpecularNoLimitDirection().apply(piece, Vectors.VERTICAL, board).stream(),
                            super.getSpecularNoLimitDirection().apply(piece, Vectors.HORIZONTAL, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a Knight. It's specific
     * of the kind of variant and GameType.
     */
    @Override
    protected PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
        return (board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT)
                    .forEach(x -> Set.of(DOUBLE_INCREMENT, -DOUBLE_INCREMENT).forEach(y -> {
                        positions
                                .addAll(super.getDestinationsFromFunction(pos -> new BoardPositionImpl(pos.getX() + x, pos.getY() + y),
                                        piece, board, SINGLE_INCREMENT));
                        positions
                                .addAll(super.getDestinationsFromFunction(pos -> new BoardPositionImpl(pos.getX() + y, pos.getY() + x),
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
    protected PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
        return (board) -> {
            return Stream.concat(
                    super.getSpecularNoLimitDirection().apply(piece, Vectors.TOP_LEFT_BOT_RIGHT, board).stream(),
                    super.getSpecularNoLimitDirection().apply(piece, Vectors.TOP_RIGHT_BOT_LEFT, board).stream())
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a Queen. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    protected PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
        return (board) -> {
            return Arrays.stream(Vectors.values())
                    .map(vector -> super.getSpecularNoLimitDirection().apply(piece, vector, board)).flatMap(Set::stream)
                    .collect(Collectors.toSet());
        };
    }

    /**
     * This method is used to get the movement strategy of a King. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    protected PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
        return (board) -> {
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getQueenMovementStrategy(piece).getPossibleMoves(board).stream()
                    .filter(pos -> this.pieceDistanceFromPositionLessThan(piece, pos, SINGLE_INCREMENT))
                    .collect(Collectors.toSet()));

            if (super.canCastle() && !piece.hasAlreadyBeenMoved()) {
                positions.addAll(Stream.concat(
                        super.getDestinationsFromFunction(pos -> new BoardPositionImpl(pos.getX() - SINGLE_INCREMENT, pos.getY()),
                                piece, board, DOUBLE_INCREMENT).stream(),
                        super.getDestinationsFromFunction(pos -> new BoardPositionImpl(pos.getX() + SINGLE_INCREMENT, pos.getY()),
                                piece, board, DOUBLE_INCREMENT).stream())
                        .collect(Collectors.toSet()));
                // Extra control on the castle
                board.getPieceAtPosition(new BoardPositionImpl(piece.getPiecePosition().getX() - DOUBLE_INCREMENT,
                        piece.getPiecePosition().getY())).ifPresent(p -> positions.remove(p.getPiecePosition()));

                board.getPieceAtPosition(new BoardPositionImpl(piece.getPiecePosition().getX() + DOUBLE_INCREMENT,
                        piece.getPiecePosition().getY())).ifPresent(p -> positions.remove(p.getPiecePosition()));
            }
            return Collections.unmodifiableSet(positions);
        };
    }

    private boolean pieceDistanceFromPositionLessThan(final Piece piece,
            final BoardPosition positionFromWhichCalculateDistance, final int distanceMathModule) {
        return this.distanceBetweenBoardPositions(piece.getPiecePosition(), positionFromWhichCalculateDistance)
                .getX() <= distanceMathModule
                && this.distanceBetweenBoardPositions(piece.getPiecePosition(), positionFromWhichCalculateDistance)
                        .getY() <= distanceMathModule;
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

}
