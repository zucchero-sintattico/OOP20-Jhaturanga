package jhaturanga.model.piece.movement;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Color;

public final class NormalPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

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
	    final int increment = piece.getPlayer().getColor().equals(Color.WHITE) ? +1 : -1;

	    final BoardPosition upLeft = new BoardPositionImpl(piece.getPiecePosition().getX() + increment,
		    piece.getPiecePosition().getY() - 1);
	    final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX() + increment,
		    piece.getPiecePosition().getY());
	    final BoardPosition upRight = new BoardPositionImpl(piece.getPiecePosition().getX() + increment,
		    piece.getPiecePosition().getY() + 1);

	    if (board.getPieceAtPosition(upRight).isEmpty() || (board.getPieceAtPosition(upLeft).isPresent()
		    && !board.getPieceAtPosition(upLeft).get().getPlayer().equals(piece.getPlayer()))) {
		positions.add(upLeft);
	    }
	    if (board.getPieceAtPosition(upFront).isEmpty()) {
		positions.add(upFront);
	    }
	    if (board.getPieceAtPosition(upRight).isEmpty() || (board.getPieceAtPosition(upRight).isPresent()
		    && !board.getPieceAtPosition(upRight).get().getPlayer().equals(piece.getPlayer()))) {
		positions.add(upRight);
	    }

	    return positions;
	};
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
	return (final Board board) -> {
	    final Set<BoardPosition> positions = new HashSet<>();
	    
	    final BoardPosition up = Stream.iterate(0, x->x+1).limit()
	};
    }

    @Override
    public PieceMovementStrategy getKnightMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getBishopMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getQueenMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

    @Override
    public PieceMovementStrategy getKingMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
    }

}
