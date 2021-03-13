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

    /**
     * Use this method to get a starting Board to play 1D chess.
     * 
     * @param whitePlayer is the whitePlayer passed
     * @param blackPlayer is the blackPlayer passed
     * @return Board representing the starting board wanted
     */
    Board oneDimensionBoard(Player whitePlayer, Player blackPlayer);

    /**
     * Use this method to get a customized Board.
     * 
     * @param whitePlayer   is the whitePlayer passed
     * @param blackPlayer   is the blackPlayer passed
     * @param startingBoard being customizable the string representing the
     *                      startingboard to create has to be passed to the factory.
     * @param columns       being customizable the number of columns need to be
     *                      passed.
     * @param rows          being customizable the number of rows need to be passed.
     * @return Board representing the starting board wanted
     */
    Board customizedBoard(String startingBoard, int columns, int rows, Player whitePlayer, Player blackPlayer);

}
