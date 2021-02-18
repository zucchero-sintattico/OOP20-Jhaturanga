package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class ClassicPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    private static final int LEFT_ROOK_COLUMN = 0;
    private static final int RIGHT_ROOK_COLUMN = 7;
    private static final int WHITE_ROOK_ROW = 0;
    private static final int BLACK_ROOK_ROW = 7;
    private static final BoardPosition WHITE_LEFT_ROOK_ORIGIN = new BoardPositionImpl(LEFT_ROOK_COLUMN, WHITE_ROOK_ROW);
    private static final BoardPosition WHITE_RIGHT_ROOK_ORIGIN = new BoardPositionImpl(RIGHT_ROOK_COLUMN,
            WHITE_ROOK_ROW);
    private static final BoardPosition BLACK_LEFT_ROOK_ORIGIN = new BoardPositionImpl(LEFT_ROOK_COLUMN, BLACK_ROOK_ROW);
    private static final BoardPosition BLACK_RIGHT_ROOK_ORIGIN = new BoardPositionImpl(RIGHT_ROOK_COLUMN,
            BLACK_ROOK_ROW);

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
            final int increment = piece.getPlayer().getColor().equals(PlayerColor.WHITE) ? SINGLE_INCREMENT
                    : -SINGLE_INCREMENT;

            positions.addAll(this
                    .fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() + increment), piece, board,
                            SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            positions.addAll(this
                    .fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() + increment), piece, board,
                            SINGLE_INCREMENT)
                    .stream()
                    .filter(x -> board.getPieceAtPosition(x).isPresent()
                            && !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
                    .collect(Collectors.toSet()));

            final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX(),
                    piece.getPiecePosition().getY() + increment);
            if (board.contains(upFront) && board.getPieceAtPosition(upFront).isEmpty()) {
                positions.add(upFront);
            }

            // Check the initial double movement for white's pawns
            if (piece.getPlayer().getColor().equals(PlayerColor.WHITE) && piece.getPiecePosition().getY() == 1) {
                final BoardPositionImpl newPosition = new BoardPositionImpl(piece.getPiecePosition().getX(),
                        piece.getPiecePosition().getY() + 2);
                if (board.getPieceAtPosition(newPosition).isEmpty()) {
                    positions.add(newPosition);
                }
            }

            // Check the initial double movement for black's pawns
            if (piece.getPlayer().getColor().equals(PlayerColor.BLACK)
                    && piece.getPiecePosition().getY() == board.getRows() - 2) {
                final BoardPositionImpl newPosition = new BoardPositionImpl(piece.getPiecePosition().getX(),
                        piece.getPiecePosition().getY() - 2);
                if (board.getPieceAtPosition(newPosition).isEmpty()) {
                    positions.add(newPosition);
                }
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
                        piece, board, board.getRows()));
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
            Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT)
                    .forEach(x -> Set.of(SINGLE_INCREMENT, -SINGLE_INCREMENT).forEach(y -> {
                        positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + x, pos.getY() + y),
                                piece, board, board.getRows() + board.getColumns()));
                    }));
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
            final Set<BoardPosition> positions = new HashSet<>();
            positions.addAll(this.getBishopMovementStrategy(piece).getPossibleMoves(board));
            positions.addAll(this.getRookMovementStrategy(piece).getPossibleMoves(board));
            return Collections.unmodifiableSet(positions);
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
            if (!piece.hasAlreadyBeenMoved()) {
                Optional<Piece> dxRook;
                if (piece.getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    dxRook = board.getPieceAtPosition(WHITE_RIGHT_ROOK_ORIGIN);
                } else {
                    dxRook = board.getPieceAtPosition(BLACK_RIGHT_ROOK_ORIGIN);
                }

                if (dxRook.isPresent() && dxRook.get().getType().equals(PieceType.ROOK)) {
                    positions.addAll(
                            this.fromFunction((position) -> new BoardPositionImpl(position.getX() + 1, position.getY()),
                                    piece, board, 2));
                }

                Optional<Piece> sxRook;
                if (piece.getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    sxRook = board.getPieceAtPosition(WHITE_LEFT_ROOK_ORIGIN);
                } else {
                    sxRook = board.getPieceAtPosition(BLACK_LEFT_ROOK_ORIGIN);
                }

                if (sxRook.isPresent() && sxRook.get().getType().equals(PieceType.ROOK)) {
                    positions.addAll(
                            this.fromFunction((position) -> new BoardPositionImpl(position.getX() - 1, position.getY()),
                                    piece, board, 3));

                    if (positions.contains(new BoardPositionImpl(piece.getPiecePosition().getX() - 3,
                            piece.getPiecePosition().getY()))) {

                        // Se contiene la terza cella allora va tolta e si puo fare l'arrocco
                        positions.remove(new BoardPositionImpl(piece.getPiecePosition().getX() - 3,
                                piece.getPiecePosition().getY()));
                    } else {
                        // Se non la contiene allora devo togliere anche la seconda
                        positions.remove(new BoardPositionImpl(piece.getPiecePosition().getX() - 2,
                                piece.getPiecePosition().getY()));
                    }
                }

            }
            return Collections.unmodifiableSet(positions);
        };
    }

    // TODO: Non dovrebbe tecnicamente tornare una BoardPosition in realtà
    private BoardPosition distanceBetweenBoardPositions(final BoardPosition p1, final BoardPosition p2) {
        return new BoardPositionImpl(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

}
