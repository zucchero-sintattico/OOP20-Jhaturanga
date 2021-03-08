package jhaturanga.model.startingboards;

import jhaturanga.model.board.Board;
import jhaturanga.model.player.Player;

public interface StartingBoardFactory {
    /**
     * Use this method to get a starting classic Board.
     * 
     * @param whitePlayer is the whitePlayer passed
     * @param blackPlayer is the blackPlayer passed
     * @return Board representing the starting board wanted
     */
    Board classicBoard(Player whitePlayer, Player blackPlayer);

    /**
     * Use this method to get a starting pawn horde Board.
     * 
     * @param whitePlayer is the whitePlayer passed
     * @param blackPlayer is the blackPlayer passed
     * @return Board representing the starting board wanted
     */
    Board pawnHordeBoard(Player whitePlayer, Player blackPlayer);

    /**
     * Use this method to get a starting three columns Board.
     * 
     * @param whitePlayer is the whitePlayer passed
     * @param blackPlayer is the blackPlayer passed
     * @return Board representing the starting board wanted
     */
    Board threeColumnsBoard(Player whitePlayer, Player blackPlayer);

}
