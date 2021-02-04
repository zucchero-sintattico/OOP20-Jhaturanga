package jhaturanga.model.pieceManagament;

public interface PieceFactory {
	
	/**
	 * @return a Piece of type Pawn
	 * 
	 * */
	Piece getPawn();
	
	/**
	 * @return a Piece of type King
	 * 
	 * */
	Piece getKing();
	
	/**
	 * @return a Piece of type Queen
	 * 
	 * */
	Piece getQueen();
	
	/**
	 * @return a Piece of type Bishop
	 * 
	 * */
	Piece getBishop();
	
	/**
	 * @return a Piece of type Knight
	 * 
	 * */
	Piece getKnight();
	
	/**
	 * @return a Piece of type Rook
	 * 
	 * */
	Piece getRook();
}
