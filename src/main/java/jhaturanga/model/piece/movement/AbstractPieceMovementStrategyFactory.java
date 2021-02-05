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

    protected final Set<BoardPosition> fromFunction(final UnaryOperator<BoardPosition> function, final Piece piece, final Board board, final int limit) {
	// la function.apply al seed della iterate serve per skippare il primo elemento
	// che è se stesso
	final List<BoardPosition> positions = Stream.iterate(function.apply(piece.getPiecePosition()), function).limit(limit)
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
}
