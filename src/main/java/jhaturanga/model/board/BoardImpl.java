package jhaturanga.model.board;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.piece.Piece;

public class BoardImpl implements Board {

    private Set<Piece> piecesOnBoard;
    private final int columns;
    private final int rows;

    public BoardImpl(final Set<Piece> startingBoard, final int columns, final int rows) {
        this.piecesOnBoard = startingBoard;
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public final Set<Piece> getBoardState() {
        return this.piecesOnBoard;
    }

    @Override
    public final Optional<Piece> getPieceAtPosition(final BoardPosition boardPosition) {
        if (!this.contains(boardPosition)) {
            throw new IllegalArgumentException();
        }
        return this.piecesOnBoard.stream().filter(x -> x.getPiecePosition().equals(boardPosition)).findFirst();
    }

    @Override
    public final boolean contains(final BoardPosition positionToCheck) {
        return positionToCheck.getX() < this.columns && positionToCheck.getY() < this.rows && positionToCheck.getX() >= 0
                && positionToCheck.getY() >= 0;
    }

    @Override
    public final int getColumns() {
        return this.columns;
    }

    @Override
    public final int getRows() {
        return this.rows;
    }

    @Override
    public final boolean removeAtPosition(final BoardPosition positionToRemove) {

        if (this.getPieceAtPosition(positionToRemove).isPresent()) {
            this.piecesOnBoard = this.piecesOnBoard.stream().filter(i -> !i.getPiecePosition().equals(positionToRemove))
                    .collect(Collectors.toSet());
//            this.remove(this.getPieceAtPosition(positionToRemove).get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final boolean remove(final Piece pieceToRemove) {
        return this.piecesOnBoard.contains(pieceToRemove) && this.piecesOnBoard.remove(pieceToRemove);
    }

    @Override
    public final boolean add(final Piece piece) {
        if (this.getPieceAtPosition(piece.getPiecePosition()).isEmpty()) {
            return this.piecesOnBoard.add(piece);
        } else {
            return false;
        }
    }

}
