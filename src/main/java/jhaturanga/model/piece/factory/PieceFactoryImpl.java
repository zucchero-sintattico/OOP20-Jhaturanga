package jhaturanga.model.piece.factory;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.player.Player;

public class PieceFactoryImpl implements PieceFactory {

    private final Player owner;

    public PieceFactoryImpl(final Player owner) {
	this.owner = owner;
    }

    @Override
    public Piece getPawn(final BoardPosition piecePosition) {
	return new PieceImpl("Pawn", piecePosition, this.owner);
    }

    @Override
    public Piece getKing(final BoardPosition piecePosition) {
	return new PieceImpl("King", piecePosition, this.owner);
    }

    @Override
    public Piece getQueen(final BoardPosition piecePosition) {
	return new PieceImpl("Queen", piecePosition, this.owner);
    }

    @Override
    public Piece getBishop(final BoardPosition piecePosition) {
	return new PieceImpl("Bishop", piecePosition, this.owner);
    }

    @Override
    public Piece getKnight(final BoardPosition piecePosition) {
	return new PieceImpl("Knight", piecePosition, this.owner);
    }

    @Override
    public Piece getRook(final BoardPosition piecePosition) {
	return new PieceImpl("Rook", piecePosition, this.owner);
    }

}
