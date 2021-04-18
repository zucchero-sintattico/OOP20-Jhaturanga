package jhaturanga.model.board;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jhaturanga.model.piece.Piece;

public final class BoardImpl implements Board {

    /**
     * Board needs to be serializable because it will be saved on the user's
     * computer through the History.
     */
    private static final long serialVersionUID = -196004388473812222L;

    /**
     * Use a List instead of a Set because pieces are mutable.
     */
    private final List<Piece> piecesOnBoard;
    private final int columns;
    private final int rows;

    public BoardImpl(final Set<Piece> startingBoard, final int columns, final int rows) {
        this.piecesOnBoard = startingBoard.stream().collect(Collectors.toList());
        this.columns = columns;
        this.rows = rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Piece> getPieces() {
        return this.piecesOnBoard.stream().collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Piece> getPieceAtPosition(final BoardPosition boardPosition) {
        return this.piecesOnBoard.stream().filter(x -> x.getPiecePosition().equals(boardPosition)).findAny();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final BoardPosition positionToCheck) {
        return positionToCheck.getX() < this.columns && positionToCheck.getY() < this.rows
                && positionToCheck.getX() >= 0 && positionToCheck.getY() >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Piece pieceToCheck) {
        return this.piecesOnBoard.contains(pieceToCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumns() {
        return this.columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRows() {
        return this.rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAtPosition(final BoardPosition positionToRemove) {
        if (this.getPieceAtPosition(positionToRemove).isPresent()) {
            return this.piecesOnBoard.remove(this.getPieceAtPosition(positionToRemove).get());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(final Piece pieceToRemove) {
        return this.piecesOnBoard.remove(pieceToRemove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(final Piece piece) {
        if (this.getPieceAtPosition(piece.getPiecePosition()).isEmpty()) {
            return this.piecesOnBoard.add(piece);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sr = new StringBuilder(
                "BoardImpl [columns=" + columns + ", rows=" + rows + ", piecesOnBoard = \n");
        this.piecesOnBoard.forEach(x -> {
            sr.append('\t');
            sr.append(x.toString());
            sr.append('\n');
        });
        return sr.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + columns;
        result = prime * result + ((piecesOnBoard == null) ? 0 : piecesOnBoard.hashCode());
        result = prime * result + rows;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final BoardImpl other = (BoardImpl) obj;

        if (columns != other.columns) {
            return false;
        }

        if (piecesOnBoard == null) {
            if (other.piecesOnBoard != null) {
                return false;
            }
        } else if (!piecesOnBoard.equals(other.piecesOnBoard)) {
            return false;
        }

        return rows == other.rows;
    }

}
