package jhaturanga.model.board;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.piece.Piece;

public class BoardImpl implements Board {

    private final Set<Piece> piecesOnBoard;
    private final int columns;
    private final int rows;

    public BoardImpl(final Set<Piece> startingBoard, final int columns, final int rows) {
	this.piecesOnBoard = startingBoard;
	this.columns = columns;
	this.rows = rows;
    }

    @Override
    public Set<Piece> getBoardState() {
	return this.piecesOnBoard;
    }

    @Override
    public Optional<Piece> getPieceAtPosition(final BoardPosition boardPosition) {
	if (!this.contains(boardPosition)) {
	    throw new IllegalArgumentException();
	}
	return this.piecesOnBoard.stream().filter(x -> x.getPiecePosition().equals(boardPosition)).findFirst();
    }

    @Override
    public boolean contains(final BoardPosition positionToCheck) {
	return positionToCheck.getX() <= this.columns && positionToCheck.getY() <= this.rows;
    }

    @Override
    public int getColumns() {
	return this.columns;
    }

    @Override
    public int getRows() {
	return this.rows;
    }

}
