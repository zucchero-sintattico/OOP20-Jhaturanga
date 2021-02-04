package jhaturanga.model.piecemanagament;

public interface PieceMovementStrategyFactory {
	
	/**
	 * @return the PieceMovementStrategy of the Pawn
	 * */
	PieceMovementStrategy getPawnMovementStrategy();
	
	/**
	 * @return the PieceMovementStrategy of the King
	 * */
	PieceMovementStrategy getKingMovementStrategy();
	
	/**
	 * @return the PieceMovementStrategy of the Queen
	 * */
	PieceMovementStrategy getQueenMovementStrategy();
	
	/**
	 * @return the PieceMovementStrategy of the Rook
	 * */
	PieceMovementStrategy getRookMovementStrategy();
	
	/**
	 * @return the PieceMovementStrategy of the Bishop
	 * */
	PieceMovementStrategy getBishopMovementStrategy();
	
	/**
	 * @return the PieceMovementStrategy of the Knight
	 * */
	PieceMovementStrategy getKnightMovementStrategy();
}
