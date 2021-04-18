package jhaturanga.model.piece.factory;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public final class PieceFactoryImpl implements PieceFactory {

    /**
     * 
     */
    private static final long serialVersionUID = 3341059957079038770L;
    private final Player owner;

    public PieceFactoryImpl(final Player owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getPawn(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.PAWN, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getKing(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.KING, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getQueen(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.QUEEN, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getBishop(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.BISHOP, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getKnight(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.KNIGHT, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getRook(final BoardPosition piecePosition) {
        return this.getPieceFromPieceType(PieceType.ROOK, piecePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Piece getPieceFromPieceType(final PieceType type, final BoardPosition piecePosition) {
        return new PieceImpl(type, piecePosition, this.owner);
    }
}
