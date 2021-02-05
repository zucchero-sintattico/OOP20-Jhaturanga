package jhaturanga.model.piece.movement;

import java.util.HashSet;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public final class NormalPieceMovementStrategyFactory extends AbstractPieceMovementStrategyFactory {

    @Override
    public PieceMovementStrategy getPawnMovementStrategy(final Piece piece) {
	// tab
	return (final Board board) -> {
	    final Set<BoardPosition> positions = new HashSet<>();

	    final BoardPosition upLeft = new BoardPositionImpl(piece.getPiecePosition().getX() - 1,
		    piece.getPiecePosition().getY() - 1);
	    final BoardPosition upFront = new BoardPositionImpl(piece.getPiecePosition().getX() - 1,
		    piece.getPiecePosition().getY());
	    final BoardPosition upRight = new BoardPositionImpl(piece.getPiecePosition().getX() - 1,
		    piece.getPiecePosition().getY() + 1);

	    if (board.getPieceAtPosition(upLeft).isPresent()
		    && !board.getPieceAtPosition(upLeft).get().getPlayer().equals(piece.getPlayer())) {
		positions.add(upLeft);
	    }
	    if (board.getPieceAtPosition(upFront).isEmpty()) {
		positions.add(upFront);
	    }
	    if (board.getPieceAtPosition(upRight).isPresent()
		    && !board.getPieceAtPosition(upRight).get().getPlayer().equals(piece.getPlayer())) {
		positions.add(upRight);
	    }

	    return positions;
	};
    }

    @Override
    public PieceMovementStrategy getRookMovementStrategy(final Piece piece) {
	return (board) -> Set.of();
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
