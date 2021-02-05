package jhaturanga.model.piece.factory;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public class PieceFactoryImpl implements PieceFactory {

    private final Player owner;

    public PieceFactoryImpl(final Player owner) {
	this.owner = owner;
    }

    @Override
    public final Piece getPawn(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.PAWN, piecePosition, this.owner);
    }

    @Override
    public final Piece getKing(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.KING, piecePosition, this.owner);
    }

    @Override
    public final Piece getQueen(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.QUEEN, piecePosition, this.owner);
    }

    @Override
    public final Piece getBishop(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.BISHOP, piecePosition, this.owner);
    }

    @Override
    public final Piece getKnight(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.KNIGHT, piecePosition, this.owner);
    }

    @Override
    public final Piece getRook(final BoardPosition piecePosition) {
	return new PieceImpl(PieceType.ROOK, piecePosition, this.owner);
    }

}
