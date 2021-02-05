package jhaturanga.model.piece.movement;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.lang.Math;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Color;

public final class NormalPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    private Set<BoardPosition> fromFunction(final UnaryOperator<BoardPosition> function, final Piece piece,
	    final Board board, final int limit) {
	// la function.apply al seed della iterate serve per skippare il primo elemento
	// che è se stesso
	List<BoardPosition> positions = Stream.iterate(function.apply(piece.getPiecePosition()), function).limit(limit)
		.takeWhile(board::contains)
		.takeWhile(x -> board.getPieceAtPosition(x).isEmpty()
			|| !board.getPieceAtPosition(x).get().getPlayer().equals(piece.getPlayer()))
		.collect(Collectors.toList());

	final Optional<BoardPosition> pos = positions.stream()
		.filter(i -> board.getPieceAtPosition(i).get().getPlayer().equals(piece.getPlayer())).findFirst();
	return pos.isEmpty() ? new HashSet<>(positions)
		: new HashSet<>(positions.subList(0, positions.indexOf(pos.get())));
    }

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
	    final int increment = piece.getPlayer().getColor().equals(Color.WHITE) ? 1 : -1;
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + increment), piece,
		    board, 1));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + increment), piece,
		    board, 1));

	    final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX(),
		    piece.getPiecePosition().getY() + increment);

	    if (board.getPieceAtPosition(upFront).isEmpty()) {
		positions.add(upFront);
	    }
	    return positions;
	};
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
	return (final Board board) -> {
	    final Set<BoardPosition> positions = new HashSet<>();
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() + 1), piece, board,
		    board.getHeight()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX(), pos.getY() - 1), piece, board,
		    board.getHeight()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY()), piece, board,
		    board.getWidth()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY()), piece, board,
		    board.getWidth()));
	    return Collections.unmodifiableSet(positions);
	};
    }

    @Override
    public PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
	return (final Board board) -> {
	    final Set<BoardPosition> positions = new HashSet<>();
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 2, pos.getY() + 1), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 2, pos.getY() - 1), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 2, pos.getY() + 1), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 2, pos.getY() - 1), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() + 2), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() - 2), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() + 2), piece, board, 1));
	    positions.addAll(
		    this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() - 2), piece, board, 1));
	    return Collections.unmodifiableSet(positions);
	};
    }

    @Override
    public PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
	return (final Board board) -> {
	    final Set<BoardPosition> positions = new HashSet<>();
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() + 1), piece,
		    board, board.getHeight() + board.getWidth()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() - 1), piece,
		    board, board.getHeight() + board.getWidth()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() - 1, pos.getY() + 1), piece,
		    board, board.getHeight() + board.getWidth()));
	    positions.addAll(this.fromFunction(pos -> new BoardPositionImpl(pos.getX() + 1, pos.getY() - 1), piece,
		    board, board.getHeight() + board.getWidth()));
	    return Collections.unmodifiableSet(positions);
	};
    }

    // TODO: CITALA NELLA RELAZIONE
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
		    .filter(i -> this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getX() <= 1
			    && this.distanceBetweenBoardPositions(piece.getPiecePosition(), i).getY() <= 1)
		    .collect(Collectors.toSet()));
	    return Collections.unmodifiableSet(positions);
	};
    }

    private BoardPosition distanceBetweenBoardPositions(final BoardPosition p1, final BoardPosition p2) {
	return new BoardPositionImpl(Math.abs(p1.getX() - p2.getX()), Math.abs(p1.getY() - p2.getY()));
    }

}
